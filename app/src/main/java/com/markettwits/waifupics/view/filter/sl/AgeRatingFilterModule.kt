package com.markettwits.waifupics.view.filter.sl

import com.markettwits.core.sl.Module
import com.markettwits.waifupics.view.filter.data.StaticCacheDataSource
import com.markettwits.waifupics.view.filter.presentation.AgeRatingFilterCommunication
import com.markettwits.waifupics.view.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.waifupics.view.filter.presentation.FilterCommunication
import com.markettwits.waifupics.view.main.domain.FilterChecked

class AgeRatingFilterModule(private val communication: FilterCommunication):
    Module<AgeRatingFilterViewModel> {
    override fun viewModel() = AgeRatingFilterViewModel(
        FilterChecked.Base(),
        AgeRatingFilterCommunication.Base(),
        communication,
        StaticCacheDataSource.Base()
    )
}