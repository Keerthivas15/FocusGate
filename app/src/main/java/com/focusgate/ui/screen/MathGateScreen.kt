package com.focusgate.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.focusgate.R
import com.focusgate.data.prefs.AppPreferences
import com.focusgate.data.repository.CreditRepository
import com.focusgate.domain.model.MathCategory
import com.focusgate.domain.model.MathProblem
import com.focusgate.math.ProblemRegistry
import com.focusgate.ui.theme.FocusGateTheme
import com.focusgate.util.UnlockStateManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── Sealed State ─────────────────────────────────────────────────────────────
sealed class MathGateUiState {
    data class Solving(
        val problem: MathProblem,
        val wrongAttempts: Int = 0,
        val hasCredits: Boolean = false,
        val allowedCategories: Set<MathCategory> = emptySet(),
    ) : MathGateUiState()
    object Unlocked : MathGateUiState()
}

// ─── ViewModel ────────────────────────────────────────────────────────────────
@HiltViewModel
class MathGateViewModel @Inject constructor(
    private val registry: ProblemRegistry,
    private val unlockManager: UnlockStateManager,
    private val creditRepo: CreditRepository,
    private val prefs: AppPreferences,
) : ViewModel() {

    private val _state = MutableStateFlow<MathGateUiState>(
        MathGateUiState.Solving(registry.next()),
    )
    val state: StateFlow<MathGateUiState> = _state

    init {
        refreshCredits()
        observeSettings()
    }

    private fun observeSettings() = viewModelScope.launch {
        prefs.selectedMathCategories.collect { stringSet ->
            val categories = stringSet.mapNotNull {
                runCatching { MathCategory.valueOf(it) }.getOrNull()
            }.toSet()
            
            val current = _state.value
            if (current is MathGateUiState.Solving) {
                _state.value = current.copy(
                    allowedCategories = categories,
                    problem = registry.next(categories)
                )
            }
        }
    }

    private fun refreshCredits() = viewModelScope.launch {
        val credits = creditRepo.availableCredits()
        val current = _state.value
        (current as? MathGateUiState.Solving)?.let {
            _state.value = it.copy(hasCredits = credits.isNotEmpty())
        }
    }

    fun submit(answer: String, targetPkg: String) = viewModelScope.launch {
        val current = (_state.value as? MathGateUiState.Solving) ?: return@launch
        if (answer.trim() == current.problem.correctAnswer) {
            val duration = prefs.unlockDurationMinutes.first()
            unlockManager.grantUnlock(targetPkg, duration)
            _state.value = MathGateUiState.Unlocked
        } else {
            val newProblem = registry.next(current.allowedCategories)
            _state.value = MathGateUiState.Solving(
                problem = newProblem,
                wrongAttempts = current.wrongAttempts + 1,
                hasCredits = current.hasCredits,
                allowedCategories = current.allowedCategories,
            )
        }
    }

    fun useCredit(targetPkg: String) = viewModelScope.launch {
        val used = creditRepo.useCredit()
        if (used) {
            val duration = prefs.unlockDurationMinutes.first()
            unlockManager.grantUnlock(targetPkg, duration)
            _state.value = MathGateUiState.Unlocked
        }
    }

    /** Called when user presses back or leaves — resets the problem. */
    fun onUserLeft() = viewModelScope.launch {
        val categories = (_state.value as? MathGateUiState.Solving)?.allowedCategories
            ?: MathCategory.entries.toSet()
        _state.value = MathGateUiState.Solving(
            problem = registry.next(categories),
            allowedCategories = categories
        )
    }
}

// ─── Activity (standalone, own task stack) ───────────────────────────────────
@AndroidEntryPoint
class MathGateActivity : ComponentActivity() {

    companion object {
        const val EXTRA_TARGET_PKG = "target_pkg"
    }

    private val viewModel: MathGateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val targetPkg = intent.getStringExtra(EXTRA_TARGET_PKG) ?: run {
            finish(); return
        }
        enableEdgeToEdge()
        setContent {
            FocusGateTheme {
                MathGateScreen(
                    targetPkg = targetPkg,
                    vm = viewModel,
                ) {
                    // Launch the target app, then finish
                    runCatching {
                        packageManager.getLaunchIntentForPackage(targetPkg)?.let { startActivity(it) }
                    }
                    finish()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        // If they leave the activity without solving, invalidate the current problem
        viewModel.onUserLeft()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        // Intentionally do nothing — back is intercepted by BackHandler in Compose
        // If we want back to reset too:
        viewModel.onUserLeft()
    }
}

// ─── Composable Screen ────────────────────────────────────────────────────────
@Composable
fun MathGateScreen(
    targetPkg: String,
    vm: MathGateViewModel = hiltViewModel(),
    onUnlocked: () -> Unit,
) {
    val state by vm.state.collectAsStateWithLifecycle()

    // Watch for unlock
    LaunchedEffect(state) {
        if (state is MathGateUiState.Unlocked) onUnlocked()
    }

    // Intercept back button — reset problem instead of going back
    androidx.activity.compose.BackHandler {
        vm.onUserLeft()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        when (val s = state) {
            is MathGateUiState.Solving -> SolvingContent(
                state = s,
                onSubmit = { vm.submit(it, targetPkg) },
            ) { vm.useCredit(targetPkg) }
            is MathGateUiState.Unlocked -> UnlockedContent()
        }
    }
}

@Composable
private fun SolvingContent(
    state: MathGateUiState.Solving,
    onSubmit: (String) -> Unit,
    onUseCredit: () -> Unit,
) {
    var input by remember(state.problem.id) { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val haptic = LocalHapticFeedback.current

    // Shake animation state
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(state.problem.id) {
        runCatching { focusRequester.requestFocus() }
    }

    // Trigger shake on wrong attempt
    LaunchedEffect(state.wrongAttempts) {
        if (state.wrongAttempts > 0) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            shakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 400
                    (-20f) at 50
                    (20f) at 100
                    (-20f) at 150
                    (20f) at 200
                    (-10f) at 250
                    (10f) at 300
                    (-5f) at 350
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .graphicsLayer(translationX = shakeOffset.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Header
        Icon(
            painter = painterResource(id = R.drawable.ic_grit_logo),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Solve to Continue",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Text(
            "Complete the problem to unlock this app",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(32.dp))

        // Problem Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        ) {
            Text(
                text = state.problem.displayText,
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.Monospace,
                    lineHeight = 26.sp,
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }

        Spacer(Modifier.height(24.dp))

        // Wrong attempt banner
        AnimatedVisibility(visible = state.wrongAttempts > 0) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Incorrect (attempt ${state.wrongAttempts}). New problem generated.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        // Answer Input
        OutlinedTextField(
            value = input,
            onValueChange = { input = it.filter { c -> (c.isDigit() || (c == '-')) } },
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            label = { Text(state.problem.inputHint) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { if (input.isNotBlank()) onSubmit(input) }
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                if (input.isNotBlank()) onSubmit(input)
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            enabled = input.isNotBlank(),
            shape = RoundedCornerShape(12.dp),
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Submit Answer", style = MaterialTheme.typography.titleMedium)
        }

        // Use Credit Button
        if (state.hasCredits) {
            Spacer(Modifier.height(12.dp))
            OutlinedButton(
                onClick = onUseCredit,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Stars, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Use a Credit (skip problem)")
            }
        }
    }
}

@Composable
private fun UnlockedContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
        Spacer(Modifier.height(16.dp))
        Text("Unlocking...", style = MaterialTheme.typography.bodyLarge)
    }
}
