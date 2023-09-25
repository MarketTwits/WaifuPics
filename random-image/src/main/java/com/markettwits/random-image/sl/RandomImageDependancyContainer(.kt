package com.markettwits.`random-image`.sl

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.filter.AgeRatingFilterModule
import com.markettwits.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.`random-image`.ui.image.ImageViewModule
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel

class RandomImageDependencyContainer(
    private val core: Core,
    private val other : DependencyContainer
) : DependencyContainer {
    val filter = FilterCommunication.Base()
    override fun module(className: Class<out ViewModel>)= when (className) {
        ImageViewModel::class.java -> ImageViewModule(core, filter)
        AgeRatingFilterViewModel::class.java -> AgeRatingFilterModule(core,filter)
        else -> other.module(className)
    }
}