package com.focusgate.data.repository

import com.focusgate.data.db.CreditDao
import com.focusgate.data.db.CreditEntity
import com.focusgate.data.prefs.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditRepository @Inject constructor(
    private val dao: CreditDao,
    private val prefs: AppPreferences
) {
    companion object {
        private const val CREDIT_VALIDITY_MS = 7 * 24 * 60 * 60 * 1000L  // 7 days
        private const val MAX_CREDITS = 3
        private const val FOCUS_HOURS_FOR_CREDIT = 2
        val FOCUS_MS_FOR_CREDIT = FOCUS_HOURS_FOR_CREDIT * 60 * 60 * 1000L
    }

    fun observeAvailableCount(): Flow<Int> =
        dao.observeCount(System.currentTimeMillis())

    suspend fun availableCredits(): List<CreditEntity> =
        dao.validCredits(System.currentTimeMillis())

    suspend fun canEarnCredit(): Boolean {
        val current = availableCredits().size
        return current < MAX_CREDITS
    }

    suspend fun addCredit() {
        val now = System.currentTimeMillis()
        dao.insert(CreditEntity(earnedAt = now, expiresAt = now + CREDIT_VALIDITY_MS))
        prefs.recordCreditEarned()
        prefs.resetFocusSession()
    }

    /** Returns true if a credit was consumed successfully. */
    suspend fun useCredit(): Boolean {
        val valid = availableCredits()
        if (valid.isEmpty()) return false
        dao.markUsed(valid.first().id)
        return true
    }

    suspend fun purgeExpired() = dao.purgeExpired(System.currentTimeMillis())
}
