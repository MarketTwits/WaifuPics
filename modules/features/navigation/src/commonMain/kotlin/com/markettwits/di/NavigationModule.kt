package com.markettwits.di

import com.markettwits.presentation.NavigationRouter
import com.markettwits.presentation.NavigationViewModel
import com.markettwits.core.di.Module
import org.koin.dsl.module

class NavigationModule(
    private val navigation: NavigationRouter
) : Module<NavigationViewModel.Base> {

    override fun viewModel() = NavigationViewModel.Base(navigation)
}
val navigationModule = module{
    single<NavigationViewModel.Base> {
        NavigationViewModel.Base(get())
    }
}