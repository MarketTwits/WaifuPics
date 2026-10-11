package com.markettwits.waifupics

import android.graphics.Color
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

internal fun ComponentActivity.enableEdgeToEdgeWithSafeArea(isDarkTheme: Boolean, imageViewer: Boolean = false) {

    WindowCompat.setDecorFitsSystemWindows(window, false)

    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT

    val insetsController = WindowInsetsControllerCompat(window, window.decorView)

    if (isDarkTheme || imageViewer) {
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = false
    } else {
        insetsController.isAppearanceLightStatusBars = true
        insetsController.isAppearanceLightNavigationBars = true
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        window.isNavigationBarContrastEnforced = false
    }

}
