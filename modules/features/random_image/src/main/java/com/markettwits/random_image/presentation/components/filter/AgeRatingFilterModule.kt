package com.markettwits.random_image.presentation.components.filter

import com.markettwits.core.Core
import com.markettwits.core.di.Module
import com.markettwits.random_image.domain.FilterChecked
import com.markettwits.random_image.presentation.components.filter.data.FilterDataSource
import com.markettwits.random_image.presentation.components.filter.presentation.AgeRatingFilterCommunication
import com.markettwits.random_image.presentation.components.filter.presentation.AgeRatingFilterViewModel
import com.markettwits.random_image.presentation.components.filter.presentation.FilterCommunication

class AgeRatingFilterModule(
    private val core : Core,
    private val communication: FilterCommunication
):
    Module<AgeRatingFilterViewModel> {
    override fun viewModel() = AgeRatingFilterViewModel(
        FilterChecked.Base(),
        AgeRatingFilterCommunication.Base(),
        communication,
        FilterDataSource.Base(core.manageResource()),
        core.bundleWrapper()
    )
}