package com.markettwits.cloud_datasource.di

import androidx.lifecycle.ViewModel
import com.markettwits.cloud_datasource.presentation.components.filter.AgeRatingFilterModule
import com.markettwits.cloud_datasource.presentation.components.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.cloud_datasource.presentation.components.filter.presentation.FilterCommunication
import com.markettwits.cloud_datasource.presentation.screen.ImageViewModel
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.di.Module
import kotlin.reflect.KClass

class RandomImageDependencyContainer(
    private val other : DependencyContainer
) : DependencyContainer {
    val filter = FilterCommunication.Base()

    override fun <T : Any> module(className: KClass<out T>): Module<ViewModel> = when (className) {
        ImageViewModel.Base::class -> ImageViewModule(filter)
        AgeRatingFilterViewModel::class -> AgeRatingFilterModule(filter)
        else -> other.module(className)
    }
}