package com.markettwits.navigation

import com.markettwits.core.sl.Module
import com.markettwits.navigation.view.NavigationViewModel

class NavigationModule : Module<NavigationViewModel> {
    override fun viewModel() = NavigationViewModel()
}