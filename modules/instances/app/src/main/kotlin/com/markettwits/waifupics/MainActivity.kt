package com.markettwits.waifupics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
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
            var darkTheme by remember { mutableStateOf(false) }
            var imageViewer by remember { mutableStateOf(false) }
            LaunchedEffect(darkTheme, imageViewer) {
                enableEdgeToEdgeWithSafeArea(darkTheme, imageViewer)
            }
            SetContentLocal(isDarkTheme = { darkTheme = it }) {
                MainScreen(
                    modifier = Modifier.semantics { testTagsAsResourceId = true },
                    onImageViewerChanged = { imageViewer = it }
                )
            }
        }
    }
}
