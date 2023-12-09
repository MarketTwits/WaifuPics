package com.markettwits.waifupics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.markettwits.core_ui.di.LocalRootPadding
import com.markettwits.core_ui.di.setContentLocal
import com.markettwits.core_ui.theme.WaifuPicsTheme
import com.markettwits.waifupics.core.MenuNavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentLocal(savedInstanceState) {
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

