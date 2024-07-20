package com.markettwits.waifupics.app

import androidx.compose.material3.Scaffold
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.markettwits.core.DefaultWaifuPicsApp
import com.markettwits.core_ui.di.SetContentLocal
import com.markettwits.core_ui.theme.WaifuPicsTheme
import com.markettwits.data.cache.InStorageFileDirectory
import com.markettwits.core.MenuNavGraph
import java.io.File

fun main() {

    InStorageFileDirectory.path = File(System.getProperty("java.io.tmpdir")).absolutePath
    DefaultWaifuPicsApp({})
    JVMExceptionWindowHandler()

    application {
        Window(
            title = "WaifuPics",
            onCloseRequest = { exitApplication() }
        ) {
            SetContentLocal {
                 MenuNavGraph()
            }
        }
    }
}
