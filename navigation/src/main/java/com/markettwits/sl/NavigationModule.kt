package com.markettwits.sl

import com.markettwits.core.sl.Module
import com.markettwits.presentation.HandleNavigation
import com.markettwits.presentation.NavigationViewModel

class NavigationModule(
    private val navigation: HandleNavigation
) : Module<NavigationViewModel> {
    override fun viewModel() = NavigationViewModel(navigation)
}