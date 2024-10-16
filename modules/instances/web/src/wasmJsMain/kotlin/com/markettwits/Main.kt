package com.markettwits

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.markettwits.core_ui.provider.SetContentLocal
import com.markettwits.waifupics.root.core.DefaultWaifuPicsApp
import com.markettwits.waifupics.root.core.MainScreen

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(title = "WaifuPics") {
        DefaultWaifuPicsApp()
        SetContentLocal({}) {
            MainScreen()
        }
    }
}