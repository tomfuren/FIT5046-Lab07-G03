package com.example.zen.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = ZenGold,
    onPrimary = ZenInk,
    primaryContainer = ZenGoldMuted,
    onPrimaryContainer = ZenInk,

    secondary = ZenGoldBright,
    onSecondary = ZenInk,
    secondaryContainer = ZenPearl,
    onSecondaryContainer = ZenInk,

    tertiary = ZenGoldMuted,
    onTertiary = ZenInk,
    tertiaryContainer = ZenPearl,
    onTertiaryContainer = ZenInk,

    background = ZenIvory,
    onBackground = ZenInk,
    surface = ZenPorcelain,
    onSurface = ZenInk,
    surfaceVariant = ZenPearl,
    onSurfaceVariant = ZenMutedText,
    outline = Color(0xFFD8D2C7),
    error = ZenError,
    onError = Color.White,
)

private val DarkColorScheme = darkColorScheme(
    primary = ZenGold,
    onPrimary = ZenBlack,
    primaryContainer = ZenGoldMuted,
    onPrimaryContainer = ZenBlack,

    secondary = ZenGoldBright,
    onSecondary = ZenBlack,
    secondaryContainer = ZenOnyx,
    onSecondaryContainer = Color.White,

    tertiary = ZenGoldMuted,
    onTertiary = ZenBlack,
    tertiaryContainer = ZenOnyx,
    onTertiaryContainer = Color.White,

    background = ZenBlack,
    onBackground = Color.White,
    surface = ZenCharcoal,
    onSurface = Color.White,
    surfaceVariant = ZenOnyx,
    onSurfaceVariant = ZenMutedTextDark,
    outline = Color(0xFF3A3A44),
    error = ZenError,
    onError = ZenBlack,
)

@Composable
fun ZenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    /**
     * App theme wrapper.
     *
     * We intentionally do NOT use dynamic colors here so screenshots look consistent across devices.
     * We do support light/dark mode (as described in the report), controlled by the Profile switch.
     */
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
