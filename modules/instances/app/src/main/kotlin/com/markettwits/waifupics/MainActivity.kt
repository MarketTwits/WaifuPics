package com.markettwits.waifupics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.markettwits.core_ui.provider.SetContentLocal
import com.markettwits.waifupics.root.core.MainScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            SetContentLocal(isDarkTheme = {
                enableEdgeToEdgeWithSafeArea(it)
            }) {
                MainScreen()
            }
        }
    }
}

