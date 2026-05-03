package com.focusgate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.focusgate.data.prefs.AppPreferences
import com.focusgate.ui.screen.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Dashboard  : Screen("dashboard")
    object AppSelect  : Screen("app_select")
    object Credits    : Screen("credits")
    object Settings   : Screen("settings")
}

@HiltViewModel
class NavViewModel @Inject constructor(
    private val prefs: AppPreferences
) : ViewModel() {
    val isOnboardingDone: StateFlow<Boolean?> = prefs.isOnboardingDone
        .map { it }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}

@Composable
fun FocusGateNavGraph(
    navController: NavHostController = rememberNavController(),
    vm: NavViewModel = hiltViewModel()
) {
    val onboardingDone by vm.isOnboardingDone.collectAsStateWithLifecycle()
    
    if (onboardingDone == null) return // Wait for splash/data load

    val startRoute = if (onboardingDone == true) Screen.Dashboard.route else Screen.Onboarding.route

    NavHost(navController = navController, startDestination = startRoute) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onPermissionsGranted = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToAppSelect = { navController.navigate(Screen.AppSelect.route) },
                onNavigateToCredits  = { navController.navigate(Screen.Credits.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.AppSelect.route) {
            AppSelectScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Credits.route) {
            CreditsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
