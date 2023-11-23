package com.markettwits.random_image.presentation.features.filter

import com.markettwits.core.Core
import com.markettwits.core.sl.Module
import com.markettwits.random_image.domain.FilterChecked
import com.markettwits.random_image.presentation.features.filter.data.StaticCacheDataSource
import com.markettwits.random_image.presentation.features.filter.presentation.AgeRatingFilterCommunication
import com.markettwits.random_image.presentation.features.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.random_image.presentation.features.filter.presentation.FilterCommunication

class AgeRatingFilterModule(
    private val core : Core,
    private val communication: FilterCommunication
):
    Module<AgeRatingFilterViewModel> {
    override fun viewModel() = AgeRatingFilterViewModel(
        FilterChecked.Base(),
        AgeRatingFilterCommunication.Base(),
        communication,
        StaticCacheDataSource.Base(),
        core.bundleWrapper()
    )
}