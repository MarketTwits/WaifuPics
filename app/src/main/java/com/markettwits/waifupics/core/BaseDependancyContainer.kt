package com.markettwits.waifupics.core

import androidx.lifecycle.ViewModel
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.di.Module
import com.markettwits.di.NavigationModule
import com.markettwits.presentation.NavigationViewModel
import com.markettwits.waifupics.navigation.BaseRouter

class BaseDependencyContainer(
    private val dependencyContainer: DependencyContainer,
) : DependencyContainer {

    override fun <T> module(className: Class<out T>): Module<ViewModel> =
        if (className == NavigationViewModel.Base::class.java)
            NavigationModule(BaseRouter())
        else
            dependencyContainer.module(className)
}