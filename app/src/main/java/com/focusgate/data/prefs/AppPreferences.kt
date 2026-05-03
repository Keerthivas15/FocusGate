package com.focusgate.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "focusgate_prefs")

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val KEY_ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        val KEY_UNLOCK_DURATION = intPreferencesKey("unlock_duration_minutes")
        val KEY_FOCUS_SESSION_START = longPreferencesKey("focus_session_start")
        val KEY_LAST_CREDIT_EARNED = longPreferencesKey("last_credit_earned")
        val KEY_SERVICE_ENABLED = booleanPreferencesKey("service_enabled")
    }

    val isOnboardingDone: Flow<Boolean> = context.dataStore.data
        .map { it[KEY_ONBOARDING_DONE] ?: false }

    val unlockDurationMinutes: Flow<Int> = context.dataStore.data
        .map { it[KEY_UNLOCK_DURATION] ?: 15 }

    val isServiceEnabled: Flow<Boolean> = context.dataStore.data
        .map { it[KEY_SERVICE_ENABLED] ?: false }

    val focusSessionStart: Flow<Long> = context.dataStore.data
        .map { it[KEY_FOCUS_SESSION_START] ?: System.currentTimeMillis() }

    val lastCreditEarned: Flow<Long> = context.dataStore.data
        .map { it[KEY_LAST_CREDIT_EARNED] ?: 0L }

    suspend fun setOnboardingDone() {
        context.dataStore.edit { it[KEY_ONBOARDING_DONE] = true }
    }

    suspend fun setUnlockDuration(minutes: Int) {
        context.dataStore.edit { it[KEY_UNLOCK_DURATION] = minutes }
    }

    suspend fun setServiceEnabled(enabled: Boolean) {
        context.dataStore.edit { it[KEY_SERVICE_ENABLED] = enabled }
    }

    suspend fun resetFocusSession() {
        context.dataStore.edit { it[KEY_FOCUS_SESSION_START] = System.currentTimeMillis() }
    }

    suspend fun recordCreditEarned() {
        context.dataStore.edit { it[KEY_LAST_CREDIT_EARNED] = System.currentTimeMillis() }
    }
}
