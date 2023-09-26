package com.markettwits.filter

import com.markettwits.core.Core
import com.markettwits.core.sl.Module
import com.markettwits.filter.data.StaticCacheDataSource
import com.markettwits.filter.presentation.AgeRatingFilterCommunication
import com.markettwits.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.`random-image`.domain.FilterChecked

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