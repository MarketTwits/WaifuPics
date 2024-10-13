package com.markettwits.waifupics.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.markettwits.core_ui.di.SetContentLocal
import com.markettwits.waifupics.root.core.DefaultWaifuPicsApp
import com.markettwits.waifupics.root.core.MainScreen

fun main() {

    JVMExceptionWindowHandler()
    DefaultWaifuPicsApp()
    application {
        Window(
            title = "WaifuPics",
            onCloseRequest = { exitApplication() }
        ) {
            SetContentLocal({}) {
                 MainScreen()
            }
        }
    }
}
