package com.markettwits.waifupics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.markettwits.core.MenuNavGraph
import com.markettwits.core.initKoin
import com.markettwits.core_ui.di.SetContentLocal
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            SetContentLocal {
                MenuNavGraph()
            }
        }
    }
}

