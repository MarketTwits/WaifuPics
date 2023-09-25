package com.markettwits.waifupics.core

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.core.sl.Module
import com.markettwits.waifupics.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.waifupics.filter.presentation.FilterCommunication
import com.markettwits.waifupics.filter.sl.AgeRatingFilterModule
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel
import com.markettwits.waifupics.view.main.ui.image.ImageViewModule

class MainDependencyContainer(
    private val core: Core,
    private val other: DependencyContainer
) : DependencyContainer {
    val filter = FilterCommunication.Base()
    val navigation = BaseHandleNavigation()
    override fun module(className: Class<out ViewModel>): Module<out ViewModel> = when (className) {
        ImageViewModel::class.java -> ImageViewModule(core, filter)
        AgeRatingFilterViewModel::class.java -> AgeRatingFilterModule(core,filter)
        else -> other.module(className)
    }
}


