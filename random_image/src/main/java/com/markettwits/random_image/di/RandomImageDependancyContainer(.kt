package com.markettwits.random_image.di

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.di.DependencyContainer
import com.markettwits.random_image.presentation.features.filter.AgeRatingFilterModule
import com.markettwits.random_image.presentation.features.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.random_image.presentation.features.filter.presentation.FilterCommunication
import com.markettwits.random_image.presentation.random_image_screen.ImageViewModel

class RandomImageDependencyContainer(
    private val core: Core,
    private val other : DependencyContainer
) : DependencyContainer {
    val filter = FilterCommunication.Base()
    override fun module(className: Class<out ViewModel>)= when (className) {
        ImageViewModel.Base::class.java -> ImageViewModule(core, filter)
        AgeRatingFilterViewModel::class.java -> AgeRatingFilterModule(core,filter)
        else -> other.module(className)
    }
}