package com.focusgate.util

import android.os.SystemClock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnlockStateManager @Inject constructor(
    private val timeProvider: TimeProvider
) {
    // packageName -> expiry epoch ms
    private val unlocks = mutableMapOf<String, Long>()

    fun isUnlocked(pkg: String): Boolean {
        val expiry = unlocks[pkg] ?: return false
        return timeProvider.nowMs() < expiry
    }

    fun grantUnlock(pkg: String, durationMinutes: Int) {
        unlocks[pkg] = timeProvider.nowMs() + durationMinutes * 60_000L
    }

    fun revokeAll() = unlocks.clear()

    fun expireIfNeeded(pkg: String) {
        if (!isUnlocked(pkg)) unlocks.remove(pkg)
    }
}

@Singleton
class TimeProvider @Inject constructor() {
    private var lastWallMs = System.currentTimeMillis()
    private var lastElapsedMs = SystemClock.elapsedRealtime()

    fun nowMs(): Long = System.currentTimeMillis()

    /** Detects if the user manually moved the clock forward. */
    fun isClockManipulated(): Boolean {
        val wallDelta = System.currentTimeMillis() - lastWallMs
        val elapsedDelta = SystemClock.elapsedRealtime() - lastElapsedMs
        return (wallDelta - elapsedDelta) > 5 * 60_000L
    }

    fun update() {
        lastWallMs = System.currentTimeMillis()
        lastElapsedMs = SystemClock.elapsedRealtime()
    }
}
