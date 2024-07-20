package com.markettwits

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.markettwits.core.DefaultWaifuPicsApp
import com.markettwits.core.MenuNavGraph
import com.markettwits.core.initKoinApp
import com.markettwits.core_ui.di.SetContentLocal
import com.markettwits.core_ui.theme.WaifuPicsTheme

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(title = "Waifupics") {
        initKoinApp()
        SetContentLocal {
            WaifuPicsTheme {
                MenuNavGraph()
            }
        }
    }
}