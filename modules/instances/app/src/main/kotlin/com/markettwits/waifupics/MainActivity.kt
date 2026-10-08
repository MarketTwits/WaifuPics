package com.markettwits.waifupics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
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
                MainScreen(modifier = Modifier.semantics { testTagsAsResourceId = true })
            }
        }
    }
}
