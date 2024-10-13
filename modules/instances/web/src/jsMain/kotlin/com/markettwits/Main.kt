package com.markettwits

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.markettwits.waifupics.root.core.DefaultWaifuPicsApp
import com.markettwits.waifupics.root.core.MainScreen
import com.markettwits.core_ui.di.SetContentLocal
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow(title = "WaifuPics") {
            DefaultWaifuPicsApp()
            SetContentLocal({}) {
                MainScreen()
            }
        }
    }
}