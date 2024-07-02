package com.markettwits.cloud_datasource.presentation.components.filter

import com.markettwits.cloud_datasource.domain.FilterChecked
import com.markettwits.cloud_datasource.presentation.components.filter.data.FilterDataSource
import com.markettwits.cloud_datasource.presentation.components.filter.presentation.AgeRatingFilterCommunication
import com.markettwits.cloud_datasource.presentation.components.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.cloud_datasource.presentation.components.filter.presentation.FilterCommunication
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