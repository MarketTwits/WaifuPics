package com.markettwits.waifupics.core

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.core.sl.Module
import com.markettwits.navigation.NavigationModule
import com.markettwits.navigation.view.NavigationViewModel
import com.markettwits.waifupics.view.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.waifupics.view.filter.presentation.FilterCommunication
import com.markettwits.waifupics.view.filter.sl.AgeRatingFilterModule
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel
import com.markettwits.waifupics.view.main.ui.image.ImageViewModule

class MainDependencyContainer(
    private val core: Core,
    private val other: DependencyContainer
) : DependencyContainer {
    val filter = FilterCommunication.Base()
    override fun module(className: Class<out ViewModel>): Module<out ViewModel> = when (className) {
        NavigationViewModel::class.java -> NavigationModule()
        ImageViewModel::class.java -> ImageViewModule(core, filter)
        AgeRatingFilterViewModel::class.java -> AgeRatingFilterModule(core,filter)
        else -> other.module(className)
    }
}


