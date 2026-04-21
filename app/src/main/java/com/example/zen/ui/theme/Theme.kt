package com.example.zen.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = ZenSageGreen,
    onPrimary = ZenOnPrimary,
    primaryContainer = ZenSageGreenContainer,
    onPrimaryContainer = ZenDeepNavy,
    secondary = ZenPastelBlue,
    onSecondary = ZenOnSecondary,
    secondaryContainer = ZenPastelBlueContainer,
    onSecondaryContainer = ZenDeepNavy,
    tertiary = ZenWarmSand,
    onTertiary = ZenOnTertiary,
    tertiaryContainer = ZenWarmSandContainer,
    onTertiaryContainer = ZenDeepNavy,
    background = ZenOffWhite,
    onBackground = ZenOnSurface,
    surface = ZenPureWhite,
    onSurface = ZenOnSurface,
)

private val DarkColorScheme = darkColorScheme(
    primary = ZenSageGreen,
    onPrimary = ZenOnPrimary,
    primaryContainer = ZenDeepNavy,
    onPrimaryContainer = ZenSageGreenContainer,
    secondary = ZenPastelBlue,
    onSecondary = ZenOnSecondary,
    secondaryContainer = ZenDeepNavy,
    onSecondaryContainer = ZenPastelBlueContainer,
    tertiary = ZenWarmSand,
    onTertiary = ZenOnTertiary,
    tertiaryContainer = ZenDeepNavy,
    onTertiaryContainer = ZenWarmSandContainer,
    background = ZenDeepNavy,
    onBackground = ZenOnSurfaceDark,
    surface = ZenSlateGray,
    onSurface = ZenOnSurfaceDark,
)

@Composable
fun ZenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
