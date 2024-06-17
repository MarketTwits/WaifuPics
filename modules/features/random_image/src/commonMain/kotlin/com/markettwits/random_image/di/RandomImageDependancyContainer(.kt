package com.markettwits.random_image.di

import androidx.lifecycle.ViewModel
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.di.Module
import com.markettwits.random_image.presentation.components.filter.AgeRatingFilterModule
import com.markettwits.random_image.presentation.components.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.random_image.presentation.components.filter.presentation.FilterCommunication
import com.markettwits.random_image.presentation.screen.ImageViewModel

class RandomImageDependencyContainer(
    private val other : DependencyContainer
) : DependencyContainer {
    val filter = FilterCommunication.Base()

    override fun <T> module(className: Class<out T>): Module<ViewModel> = when (className) {
        ImageViewModel.Base::class.java -> ImageViewModule(filter)
        AgeRatingFilterViewModel::class.java -> AgeRatingFilterModule(filter)
        else -> other.module(className)
    }
}