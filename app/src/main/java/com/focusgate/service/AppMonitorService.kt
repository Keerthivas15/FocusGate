package com.focusgate.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.focusgate.BuildConfig
import com.focusgate.R
import com.focusgate.data.prefs.AppPreferences
import com.focusgate.data.repository.BlockedAppRepository
import com.focusgate.data.repository.CreditRepository
import com.focusgate.ui.screen.MathGateActivity
import com.focusgate.util.PermissionHelper
import com.focusgate.util.TimeProvider
import com.focusgate.util.UnlockStateManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AppMonitorService : LifecycleService() {

    @Inject lateinit var blockedRepo: BlockedAppRepository
    @Inject lateinit var unlockManager: UnlockStateManager
    @Inject lateinit var timeProvider: TimeProvider
    @Inject lateinit var creditRepo: CreditRepository
    @Inject lateinit var prefs: AppPreferences

    private val usageStats by lazy {
        getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    }

    private var monitorJob: Job? = null

    // Track focus session for credit earning
    private var focusSessionStartMs = System.currentTimeMillis()

    companion object {
        const val CHANNEL_ID = "focusgate_monitor"
        const val NOTIF_ID = 101
        const val POLL_INTERVAL_MS = 750L
        const val CREDIT_FOCUS_THRESHOLD_MS = 2 * 60 * 60 * 1000L // 2 hours
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        createNotificationChannel()
        startForegroundWithNotification()
        startMonitoring()
        return START_STICKY
    }

    private fun startMonitoring() {
        monitorJob?.cancel()
        monitorJob = lifecycleScope.launch(Dispatchers.Default) {
            while (true) {
                // Anti-cheat: detect time manipulation
                if (timeProvider.isClockManipulated()) {
                    unlockManager.revokeAll()
                }
                timeProvider.update()

                if (!PermissionHelper.hasUsageStatsPermission(applicationContext)) {
                    delay(5000)
                    continue
                }

                val blocked = blockedRepo.getBlockedPackages().toSet()
                val fg = getForegroundPackage()

                if (fg != null && fg in blocked && fg != BuildConfig.APPLICATION_ID) {
                    unlockManager.expireIfNeeded(fg)
                    if (!unlockManager.isUnlocked(fg)) {
                        // Check for idle bonus
                        val lastOpen = prefs.lastAppOpenTime.first()
                        val idleMs = System.currentTimeMillis() - lastOpen
                        val bonusMins = creditRepo.calculateIdleBonusMinutes(idleMs)
                        
                        if (bonusMins > 0) {
                            unlockManager.grantUnlock(fg, bonusMins)
                            showIdleBonusNotification(bonusMins)
                        } else {
                            launchMathGate(fg)
                            focusSessionStartMs = System.currentTimeMillis() // reset streak
                        }
                    }
                    // Update last open time whenever a blocked app is in use
                    prefs.setLastAppOpenTime(System.currentTimeMillis())
                } else {
                    // Not using a blocked app — track focus time
                    val focusMs = System.currentTimeMillis() - focusSessionStartMs
                    if (focusMs >= CREDIT_FOCUS_THRESHOLD_MS) {
                        tryEarnCredit()
                    }
                }

                if (fg == BuildConfig.APPLICATION_ID) {
                    prefs.setLastAppOpenTime(System.currentTimeMillis())
                }

                delay(POLL_INTERVAL_MS)
            }
        }
    }

    private suspend fun tryEarnCredit() {
        if (creditRepo.canEarnCredit()) {
            creditRepo.addCredit()
            focusSessionStartMs = System.currentTimeMillis()
            showCreditEarnedNotification()
        }
    }

    private fun getForegroundPackage(): String? {
        val now = System.currentTimeMillis()
        val stats = usageStats.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            now - 2000,
            now
        )
        return stats?.maxByOrNull { it.lastTimeUsed }?.packageName
    }

    private fun launchMathGate(targetPkg: String) {
        val i = Intent(this, MathGateActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            putExtra(MathGateActivity.EXTRA_TARGET_PKG, targetPkg)
        }
        startActivity(i)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Grit Active",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Shows while Grit is monitoring apps"
            setShowBadge(false)
        }
        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }

    private fun startForegroundWithNotification() {
        val notification = buildMonitorNotification()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                NOTIF_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(NOTIF_ID, notification)
        }
    }

    private fun buildMonitorNotification() = NotificationCompat
        .Builder(this, CHANNEL_ID)
        .setContentTitle("Grit is active")
        .setContentText("Monitoring your focus session")
        .setSmallIcon(R.drawable.ic_shield)
        .setOngoing(true)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .build()

    private fun showCreditEarnedNotification() {
        val n = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🎉 Credit Earned!")
            .setContentText("You stayed focused for 2 hours. A credit has been added.")
            .setSmallIcon(R.drawable.ic_shield)
            .setAutoCancel(true)
            .build()
        getSystemService(NotificationManager::class.java).notify(202, n)
    }

    private fun showIdleBonusNotification(minutes: Int) {
        val n = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("🎁 Welcome Back!")
            .setContentText("You've been away for 10+ hours. Enjoy $minutes mins of free access.")
            .setSmallIcon(R.drawable.ic_shield)
            .setAutoCancel(true)
            .build()
        getSystemService(NotificationManager::class.java).notify(203, n)
    }

    override fun onDestroy() {
        monitorJob?.cancel()
        super.onDestroy()
    }
}
