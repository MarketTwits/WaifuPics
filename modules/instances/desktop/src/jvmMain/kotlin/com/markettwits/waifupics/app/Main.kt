package com.markettwits.waifupics.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.markettwits.core.DefaultWaifuPicsApp
import com.markettwits.core.MenuNavGraph
import com.markettwits.core_ui.di.SetContentLocal
import com.markettwits.cache_datasource.kstore.InStorageFileDirectory
import java.io.File

fun main() {

    InStorageFileDirectory.path = File(System.getProperty("java.io.tmpdir")).absolutePath
    JVMExceptionWindowHandler()
    DefaultWaifuPicsApp()
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
