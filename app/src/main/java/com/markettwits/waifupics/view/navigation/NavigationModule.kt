package com.markettwits.waifupics.view.navigation

import com.markettwits.core.sl.Module
import com.markettwits.waifupics.view.navigation.view.NavigationViewModel

class NavigationModule : Module<NavigationViewModel> {
    override fun viewModel() = NavigationViewModel()
}