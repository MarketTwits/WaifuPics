package com.markettwits

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.markettwits.core.DefaultWaifuPicsApp
import com.markettwits.core.MenuNavGraph
import com.markettwits.core_ui.di.SetContentLocal
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow(title = "NYTime-KMP") {
            DefaultWaifuPicsApp()
            SetContentLocal {
                MenuNavGraph()
            }
        }
    }
}