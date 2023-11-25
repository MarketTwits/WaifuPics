package com.markettwits.presentation.screens.detail.window_controller

import android.app.Activity
import android.view.Window
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
@Composable
fun rememberWindowInsetsController(): WindowController {
    val window = with(LocalContext.current as Activity) { return@with window }
    val systemUiController = rememberSystemUiController()
    val localColor = MaterialTheme.colorScheme.background
    return remember { WindowController(window, systemUiController, localColor) }
}

class WindowController(
    private val window: Window,
    private val systemUiController: SystemUiController,
    private val localColor: Color
) {
    fun handle(edgeToEdge: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, edgeToEdge)
        systemUiController.setSystemBarsColor(
            color = if (edgeToEdge) localColor else Color.Transparent
        )
    }
}
