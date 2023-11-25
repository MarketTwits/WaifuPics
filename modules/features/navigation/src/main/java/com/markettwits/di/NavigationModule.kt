package com.markettwits.di

import com.markettwits.core.di.Module
import com.markettwits.presentation.NavigationRouter
import com.markettwits.presentation.NavigationViewModel

class NavigationModule(
    private val navigation: NavigationRouter
) : Module<NavigationViewModel.Base> {
    override fun viewModel() = NavigationViewModel.Base(navigation)
}