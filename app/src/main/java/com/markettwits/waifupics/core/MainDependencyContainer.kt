package com.markettwits.waifupics.core

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.core.sl.Module
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel
import com.markettwits.waifupics.view.main.ui.image.ImageViewModule
import com.markettwits.waifupics.view.navigation.NavigationModule
import com.markettwits.waifupics.view.navigation.view.NavigationViewModel

class MainDependencyContainer(
    private val core: Core,
    private val other: DependencyContainer
) : DependencyContainer {
    override fun module(className: Class<out ViewModel>): Module<out ViewModel> = when (className) {
        NavigationViewModel::class.java -> NavigationModule()
        ImageViewModel::class.java -> ImageViewModule(core)
        else -> other.module(className)
    }

}


