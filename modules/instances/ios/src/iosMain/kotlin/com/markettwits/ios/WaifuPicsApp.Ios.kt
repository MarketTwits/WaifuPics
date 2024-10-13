package com.markettwits.ios

import androidx.compose.ui.window.ComposeUIViewController
import com.markettwits.waifupics.root.core.MainScreen
import com.markettwits.waifupics.root.core.initKoinApp
import com.markettwits.core_ui.di.SetContentLocal
import com.markettwits.cache.kstore.InStorageFileDirectory
import io.github.xxfast.kstore.file.utils.CachesDirectory
import io.github.xxfast.kstore.utils.ExperimentalKStoreApi
import platform.Foundation.NSFileManager
import platform.UIKit.UIViewController

fun MainViewController() : UIViewController = ComposeUIViewController {
    InitStorage()
    initKoinApp()
    SetContentLocal({}) {
        MainScreen()
    }
}

@OptIn(ExperimentalKStoreApi::class)
private fun InitStorage(){
    InStorageFileDirectory.path = NSFileManager.defaultManager.CachesDirectory?.relativePath.toString()
}

