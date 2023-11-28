package com.markettwits.core_ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Color.Black,
    secondary = DarkGrey,
    surfaceTint = Color.White,
    tertiary = Color.Black,
    primaryContainer = Color.DarkGray,
    surface = Color.Black,
    onPrimary = DirtyWhite,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    background = Color.Black,
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = PinkMilk,
    surfaceTint = LightPink,
    primaryContainer = LightPink,
    tertiary = Color.White,
    surface = Color.White,
    onPrimary = Color.DarkGray,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Pink,
    onSurface = Color.White,
    background = Color.White,
)

@Composable
fun WaifuPicsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setNavigationBarColor(
            color = if (darkTheme) Color.Black else Color.White
        )
        systemUiController.setStatusBarColor(
            color = if (darkTheme) Color.Transparent else Color.White
        )
    }
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

