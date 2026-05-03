package com.focusgate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ─── Colors ───────────────────────────────────────────────────────────────────
val Blue700   = Color(0xFF1A56DB)
val Blue500   = Color(0xFF3F83F8)
val Blue100   = Color(0xFFE1EFFE)
val Surface   = Color(0xFFF8FAFC)
val OnSurface = Color(0xFF1E293B)
val ErrorRed  = Color(0xFFEF4444)
val SuccessGreen = Color(0xFF22C55E)

private val LightColors = lightColorScheme(
    primary         = Blue700,
    onPrimary       = Color.White,
    primaryContainer = Blue100,
    onPrimaryContainer = Blue700,
    secondary       = Blue500,
    onSecondary     = Color.White,
    background      = Surface,
    onBackground    = OnSurface,
    surface         = Color.White,
    onSurface       = OnSurface,
    error           = ErrorRed,
    onError         = Color.White
)

private val DarkColors = darkColorScheme(
    primary         = Blue500,
    onPrimary       = Color.White,
    primaryContainer = Color(0xFF1E3A8A),
    onPrimaryContainer = Blue100,
    secondary       = Blue500,
    onSecondary     = Color.White,
    background      = Color(0xFF0F172A),
    onBackground    = Color(0xFFE2E8F0),
    surface         = Color(0xFF1E293B),
    onSurface       = Color(0xFFE2E8F0),
    error           = ErrorRed,
    onError         = Color.White
)

@Composable
fun FocusGateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography  = Typography(),
        content     = content
    )
}
