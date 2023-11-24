package com.markettwits.di

import com.markettwits.core.di.Module
import com.markettwits.presentation.HandleNavigation
import com.markettwits.presentation.NavigationViewModel

class NavigationModule(
    private val navigation: HandleNavigation
) : Module<NavigationViewModel> {
    override fun viewModel() = NavigationViewModel()
}