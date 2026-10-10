package com.markettwits.theme.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import com.markettwits.waifupics.settings.params.ColorTheme

@Composable
fun systemColorPallet(
    systemIsDark: Boolean = isSystemInDarkTheme(),
    theme: ColorTheme,
    animateColors: Boolean = true,
): ColorScheme {
    return when (theme) {
        ColorTheme.DarkTheme -> DarkColorScheme
        ColorTheme.LightTheme -> LightColorScheme
        ColorTheme.System -> if (systemIsDark) DarkColorScheme else LightColorScheme
        ColorTheme.Empty -> if (systemIsDark) DarkColorScheme else LightColorScheme
    }.let { if (animateColors) it.toAnimatePallet() else it }
}

@Composable
internal fun isDark(
    systemIsDark: Boolean = isSystemInDarkTheme(),
    theme: ColorTheme,
): Boolean {
    return when (theme) {
        ColorTheme.DarkTheme -> true
        ColorTheme.LightTheme -> false
        ColorTheme.System -> systemIsDark
        ColorTheme.Empty -> systemIsDark
    }
}
