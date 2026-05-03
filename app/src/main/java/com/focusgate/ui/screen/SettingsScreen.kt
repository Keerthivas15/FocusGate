package com.focusgate.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.focusgate.data.prefs.AppPreferences
import com.focusgate.domain.model.MathCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val prefs: AppPreferences
) : ViewModel() {

    val unlockDuration: StateFlow<Int> = prefs.unlockDurationMinutes
        .stateIn(viewModelScope, SharingStarted.Eagerly, 15)

    val selectedCategories: StateFlow<Set<MathCategory>> = prefs.selectedMathCategories
        .map { strings -> strings.mapNotNull { runCatching { MathCategory.valueOf(it) }.getOrNull() }.toSet() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, MathCategory.entries.toSet())

    fun setUnlockDuration(minutes: Int) = viewModelScope.launch {
        prefs.setUnlockDuration(minutes)
    }

    fun toggleCategory(category: MathCategory) = viewModelScope.launch {
        val current = selectedCategories.value.toMutableSet()
        if (current.contains(category)) {
            current.remove(category)
        } else {
            current.add(category)
        }
        prefs.setSelectedMathCategories(current.map { it.name }.toSet())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    vm: SettingsViewModel = hiltViewModel()
) {
    val duration by vm.unlockDuration.collectAsStateWithLifecycle()
    val selectedCats by vm.selectedCategories.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 2.dp) {
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = selectedCats.isNotEmpty(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("OK", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            
            Text(
                "Math Problems",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Choose the types of math problems you want to solve to unlock apps.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(16.dp))

                    MathCategory.entries.forEach { cat ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedCats.contains(cat),
                                onCheckedChange = { vm.toggleCategory(cat) }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(cat.displayName)
                        }
                    }
                    
                    if (selectedCats.isEmpty()) {
                        Text(
                            "Select at least one category to continue",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                        )
                    }
                }
            }

            Text(
                "Unlock Behavior",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Timer, contentDescription = null)
                        Spacer(Modifier.width(12.dp))
                        Text("Unlock Duration", style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "How long access is granted after solving a problem or using a credit.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(Modifier.height(16.dp))

                    val options = listOf(5, 10, 15, 30, 60)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        options.forEach { opt ->
                            FilterChip(
                                selected = duration == opt,
                                onClick = { vm.setUnlockDuration(opt) },
                                label = { Text("${opt}m") }
                            )
                        }
                    }
                }
            }

            // More settings can be added here (e.g. math difficulty, notification settings)
        }
    }
}
