package com.focusgate.domain.model

import java.util.UUID

// ─── App Info (for the app picker) ───────────────────────────────────────────
data class AppInfo(
    val packageName: String,
    val label: String
)

// ─── Math Problem ─────────────────────────────────────────────────────────────
data class MathProblem(
    val id: String = UUID.randomUUID().toString(),
    val displayText: String,
    val inputHint: String,
    val correctAnswer: String,
    val type: ProblemType
)

enum class ProblemType { DETERMINANT, DOT_PRODUCT, MATRIX_MULTIPLY }

// ─── Unlock State ─────────────────────────────────────────────────────────────
sealed class UnlockState {
    object Locked : UnlockState()
    data class Unlocked(val packageName: String, val expiresAt: Long) : UnlockState()
}

// ─── Credit Balance ───────────────────────────────────────────────────────────
data class CreditBalance(val available: Int, val nextEarnAt: Long?)
