package com.markettwits.random_image.presentation.components.filter

import com.markettwits.random_image.domain.FilterChecked
import com.markettwits.random_image.presentation.components.filter.data.FilterDataSource
import com.markettwits.random_image.presentation.components.filter.presentation.AgeRatingFilterCommunication
import com.markettwits.random_image.presentation.components.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.random_image.presentation.components.filter.presentation.FilterCommunication
import com.markettwits.core.di.Module

class AgeRatingFilterModule(
    private val communication: FilterCommunication
): Module<AgeRatingFilterViewModel> {
    override fun viewModel() = AgeRatingFilterViewModel(
        filterChecked = FilterChecked.Base(),
        communication =  AgeRatingFilterCommunication.Base(),
        filterCommunication =  communication,
        defaultValue =  FilterDataSource.Base()
    )
}