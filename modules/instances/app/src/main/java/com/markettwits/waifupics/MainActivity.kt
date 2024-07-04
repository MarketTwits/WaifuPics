package com.markettwits.waifupics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.markettwits.core.MenuNavGraph
import com.markettwits.core_ui.di.SetContentLocal
import com.markettwits.core_ui.theme.WaifuPicsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        //enableEdgeToEdge()
        setContent {
            SetContentLocal(this.applicationContext as WaifuPicsApp) {
                WaifuPicsTheme {
                    MenuNavGraph()
                }
            }
        }
    }
}

