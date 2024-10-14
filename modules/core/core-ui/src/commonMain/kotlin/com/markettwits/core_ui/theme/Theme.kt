package com.markettwits.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.core_ui.theme.theme.components.isDark
import com.markettwits.core_ui.theme.theme.components.systemColorPallet
import com.markettwits.core_ui.theme.theme.viewmodel.ThemeViewModel

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
    isDarkTheme: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    val viewModel = ApplicationViewModel<ThemeViewModel>()
    val theme = viewModel.theme().collectAsState()
        //  if (theme.value != ColorTheme.Empty) {
        isDarkTheme(isDark(theme = theme.value))
        val palette = systemColorPallet(theme = theme.value, systemIsDark = isSystemInDarkTheme())
        MaterialTheme(
            colorScheme = palette,
            typography = Typography,
            content = content
        )
            //   }
}
