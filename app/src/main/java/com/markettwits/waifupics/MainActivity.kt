package com.markettwits.waifupics

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.markettwits.core.wrappers.SaveAndRestore
import com.markettwits.core.wrappers.WrapBundle
import com.markettwits.core_ui.LocalRootPadding
import com.markettwits.core_ui.setContentLocal
import com.markettwits.core_ui.theame.theme.WaifuPicsTheme
import com.markettwits.waifupics.core.MenuNavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val firstRun = savedInstanceState == null
        (application as SaveAndRestore).init(firstRun)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        (application as SaveAndRestore).save(WrapBundle.Base(outState))
        Log.d("mt05", "onSaveInstanceState #MainActivity")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        (application as SaveAndRestore).restore(WrapBundle.Base(savedInstanceState))
        Log.d("mt05", "restoreState #MainActivity")
    }
}

