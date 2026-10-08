package com.markettwits.theme.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.markettwits.theme.viewmodel.ThemeViewModel

val LocalWaifuDarkTheme = staticCompositionLocalOf { false }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WaifuPicsTheme(
    viewModel: ThemeViewModel,
    isDarkTheme: (Boolean) -> Unit,
    content: @Composable () -> Unit,
) {
    val theme = viewModel.theme().collectAsState()
    val dark = isDark(theme = theme.value)
    LaunchedEffect(dark) { isDarkTheme(dark) }
    CompositionLocalProvider(LocalWaifuDarkTheme provides dark) {
        MaterialExpressiveTheme(
            colorScheme = systemColorPallet(theme = theme.value, systemIsDark = isSystemInDarkTheme()),
            motionScheme = MotionScheme.expressive(),
            shapes = Shapes,
            typography = waifuTypography(),
            content = content
        )
    }
}
