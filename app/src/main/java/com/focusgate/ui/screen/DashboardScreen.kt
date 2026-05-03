package com.focusgate.ui.screen

import android.content.Intent
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.focusgate.R
import com.focusgate.data.prefs.AppPreferences
import com.focusgate.data.repository.BlockedAppRepository
import com.focusgate.data.repository.CreditRepository
import com.focusgate.service.AppMonitorService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val blockedRepo: BlockedAppRepository,
    private val creditRepo: CreditRepository,
    private val prefs: AppPreferences
) : ViewModel() {

    val blockedCount: StateFlow<Int> = blockedRepo.blockedPackages
        .map { it.size }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val creditCount: StateFlow<Int> = creditRepo.observeAvailableCount()
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val isServiceEnabled: StateFlow<Boolean> = prefs.isServiceEnabled
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleService(enabled: Boolean) = viewModelScope.launch {
        prefs.setServiceEnabled(enabled)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToAppSelect: () -> Unit,
    onNavigateToCredits: () -> Unit,
    onNavigateToSettings: () -> Unit,
    vm: DashboardViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val blockedCount by vm.blockedCount.collectAsStateWithLifecycle()
    val creditCount by vm.creditCount.collectAsStateWithLifecycle()
    val serviceEnabled by vm.isServiceEnabled.collectAsStateWithLifecycle()

    LaunchedEffect(serviceEnabled) {
        val intent = Intent(context, AppMonitorService::class.java)
        if (serviceEnabled) {
            ContextCompat.startForegroundService(context, intent)
        } else {
            context.stopService(intent)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Grit", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── Service Toggle Card ──────────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (serviceEnabled)
                        MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.size(56.dp)
                            .background(
                                if (serviceEnabled) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.outline,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_grit_logo),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            if (serviceEnabled) "Protection Active" else "Protection Off",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            if (serviceEnabled) "Grit is watching" else "Tap to enable",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                    Switch(
                        checked = serviceEnabled,
                        onCheckedChange = { vm.toggleService(it) }
                    )
                }
            }

            // ── Stats Row ───────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    label = "Apps Blocked",
                    value = blockedCount.toString(),
                    icon = Icons.Default.Block,
                    onClick = onNavigateToAppSelect
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    label = "Credits",
                    value = creditCount.toString(),
                    icon = Icons.Default.Stars,
                    onClick = onNavigateToCredits
                )
            }

            // ── Quick Action Cards ───────────────────────────────────────────
            Text(
                "Quick Actions",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            ActionCard(
                icon = Icons.Default.AppBlocking,
                title = "Manage Blocked Apps",
                subtitle = "$blockedCount apps selected",
                onClick = onNavigateToAppSelect
            )
            ActionCard(
                icon = Icons.Default.EmojiEvents,
                title = "My Credits",
                subtitle = "$creditCount credits available",
                onClick = onNavigateToCredits
            )
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }
    }
}

@Composable
private fun ActionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
        }
    }
}
