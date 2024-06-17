package com.markettwits.waifupics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.markettwits.core_ui.di.LocalRootPadding
import com.markettwits.core_ui.di.SetContentLocal
import com.markettwits.core_ui.theme.WaifuPicsTheme
import com.markettwits.waifupics.core.MenuNavGraph
import com.markettwits.waifupics.core.WaifuPicsApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            SetContentLocal(this.applicationContext as WaifuPicsApp) {
                WaifuPicsTheme {
                    Scaffold {
                        CompositionLocalProvider(LocalRootPadding provides it) {
                            MenuNavGraph()
                        }
                    }
                }
            }
        }
    }
}

