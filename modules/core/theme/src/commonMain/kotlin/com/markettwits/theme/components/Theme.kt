package com.markettwits.theme.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.markettwits.theme.viewmodel.ThemeViewModel

internal val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    secondary = DarkGrey,
    surfaceTint = Color.White,
    tertiary = Color.Black,
    primaryContainer = Color.DarkGray,
    surface = Color.Black,
    inversePrimary = Color.White,
    onPrimary = DirtyWhite,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    background = Color.Black,
)

internal val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = PinkMilk,
    surfaceTint = LightPink,
    primaryContainer = LightPink,
    tertiary = Color.White,
    surface = Color.White,
    inversePrimary = Color.Black,
    onPrimary = Color.DarkGray,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Pink,
    onSurface = Color.White,
    background = Color.White,
)

@Composable
fun WaifuPicsTheme(
    viewModel: ThemeViewModel,
    isDarkTheme: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    val theme = viewModel.theme().collectAsState()
    isDarkTheme(isDark(theme = theme.value))
    val palette = systemColorPallet(theme = theme.value, systemIsDark = isSystemInDarkTheme())
    MaterialTheme(
        colorScheme = palette,
        typography = Typography,
        content = content
    )
}
