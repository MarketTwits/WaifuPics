package com.markettwits.waifupics.view.filter.sl

import androidx.lifecycle.ViewModel
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.waifupics.view.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.waifupics.view.filter.presentation.FilterCommunication

class AgeFilterDependencyContainer(
    private val other : DependencyContainer
) : DependencyContainer {
    override fun module(className: Class<out ViewModel>)= when (className) {
        AgeRatingFilterViewModel::class.java -> AgeRatingFilterModule(FilterCommunication.Base())
        else -> other.module(className)
    }
}