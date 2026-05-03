package com.focusgate.ui.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.compose.ui.res.painterResource
import com.focusgate.R
import com.focusgate.data.prefs.AppPreferences
import com.focusgate.util.PermissionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── ViewModel ────────────────────────────────────────────────────────────────
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val prefs: AppPreferences
) : ViewModel() {

    private val _step = MutableStateFlow(0)
    val step: StateFlow<Int> = _step

    fun nextStep() { _step.value++ }

    fun completeOnboarding() = viewModelScope.launch {
        prefs.setOnboardingDone()
    }
}

// ─── Screen ───────────────────────────────────────────────────────────────────
@Composable
fun OnboardingScreen(
    onPermissionsGranted: () -> Unit,
    vm: OnboardingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val step by vm.step.collectAsStateWithLifecycle()

    val notifLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { vm.nextStep() }

    AnimatedContent(targetState = step, label = "onboarding") { s ->
        when (s) {
            0 -> WelcomeStep { vm.nextStep() }
            1 -> PermissionStep(
                icon = Icons.Default.BarChart,
                title = "Usage Access",
                description = "Grit needs to see which app is open. This is the only way to detect when a blocked app launches.",
                buttonText = "Grant Usage Access",
                isGranted = PermissionHelper.hasUsageStatsPermission(context),
                onGrant = { PermissionHelper.openUsageStatsSettings(context) },
                onNext = { vm.nextStep() }
            )
            2 -> PermissionStep(
                icon = Icons.Default.Layers,
                title = "Display Over Apps",
                description = "Grit needs to show the Math Gate screen on top of blocked apps.",
                buttonText = "Grant Overlay Permission",
                isGranted = PermissionHelper.hasOverlayPermission(context),
                onGrant = { PermissionHelper.openOverlaySettings(context) },
                onNext = { vm.nextStep() }
            )
            3 -> PermissionStep(
                icon = Icons.Default.BatteryFull,
                title = "Battery Optimization",
                description = "Disable battery optimization so Grit keeps running in the background.",
                buttonText = "Disable Battery Optimization",
                isGranted = PermissionHelper.isBatteryOptimizationIgnored(context),
                onGrant = { PermissionHelper.openBatteryOptimizationSettings(context) },
                onNext = { vm.nextStep() }
            )
            else -> {
                LaunchedEffect(Unit) {
                    vm.completeOnboarding()
                    onPermissionsGranted()
                }
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun WelcomeStep(onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(96.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_grit_logo),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.height(32.dp))
        Text(
            "Welcome to Grit",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Take back control of your attention.\nSolve a quick math problem to unlock distracting apps — and earn credits for staying focused.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(48.dp))
        Button(onClick = onNext, modifier = Modifier.fillMaxWidth().height(52.dp)) {
            Text("Get Started", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun PermissionStep(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    buttonText: String,
    isGranted: Boolean,
    onGrant: () -> Unit,
    onNext: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(80.dp)
                .background(
                    if (isGranted) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                if (isGranted) Icons.Default.CheckCircle else icon,
                contentDescription = null,
                modifier = Modifier.size(44.dp),
                tint = if (isGranted) MaterialTheme.colorScheme.primary
                       else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text(
            description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(40.dp))
        if (!isGranted) {
            Button(onClick = onGrant, modifier = Modifier.fillMaxWidth().height(52.dp)) {
                Text(buttonText)
            }
            Spacer(Modifier.height(12.dp))
            TextButton(onClick = onNext) { Text("Skip for now") }
        } else {
            Button(onClick = onNext, modifier = Modifier.fillMaxWidth().height(52.dp)) {
                Icon(Icons.Default.ArrowForward, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Continue")
            }
        }
    }
}
