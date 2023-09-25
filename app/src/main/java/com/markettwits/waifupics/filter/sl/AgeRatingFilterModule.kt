package com.markettwits.waifupics.filter.sl

import com.markettwits.core.Core
import com.markettwits.core.sl.Module
import com.markettwits.waifupics.filter.data.StaticCacheDataSource
import com.markettwits.waifupics.filter.presentation.AgeRatingFilterCommunication
import com.markettwits.waifupics.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.waifupics.filter.presentation.FilterCommunication
import com.markettwits.waifupics.view.main.domain.FilterChecked

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