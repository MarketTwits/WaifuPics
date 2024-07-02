package com.markettwits.di

import com.markettwits.presentation.NavigationRouter
import com.markettwits.presentation.NavigationViewModel
import com.markettwits.core.di.Module

class NavigationModule(
    private val navigation: NavigationRouter
) : Module<NavigationViewModel.Base> {
    override fun viewModel() = NavigationViewModel.Base(navigation)
}