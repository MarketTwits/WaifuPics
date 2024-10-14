package com.markettwits.ios

import androidx.compose.ui.window.ComposeUIViewController
import com.markettwits.core_ui.di.SetContentLocal
import com.markettwits.waifupics.root.core.MainScreen
import com.markettwits.waifupics.root.core.initKoinApp
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController =
    ComposeUIViewController(configure = { enforceStrictPlistSanityCheck = false }) {
        SetContentLocal({}) {
            MainScreen()
        }
    }

fun initLaunch() = initKoinApp()
