package com.markettwits.ios

import androidx.compose.ui.window.ComposeUIViewController
import com.markettwits.core.MenuNavGraph
import com.markettwits.core.initKoinApp
import com.markettwits.core_ui.di.SetContentLocal
import com.markettwits.cache_datasource.kstore.InStorageFileDirectory
import io.github.xxfast.kstore.file.utils.CachesDirectory
import io.github.xxfast.kstore.utils.ExperimentalKStoreApi
import platform.Foundation.NSFileManager

fun MainViewController() = ComposeUIViewController {
    InitStorage()
    initKoinApp()
    SetContentLocal({}) {
        MenuNavGraph()
    }
}

@OptIn(ExperimentalKStoreApi::class)
private fun InitStorage(){
    InStorageFileDirectory.path = NSFileManager.defaultManager.CachesDirectory?.relativePath.toString()
}

