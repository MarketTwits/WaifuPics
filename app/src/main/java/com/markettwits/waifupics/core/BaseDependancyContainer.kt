package com.markettwits.waifupics.core

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.di.DependencyContainer
import com.markettwits.di.NavigationModule
import com.markettwits.presentation.NavigationViewModel
import com.markettwits.waifupics.navigation.BaseRouter

class BaseDependencyContainer(
    private val core: Core,
    private val dependencyContainer: DependencyContainer,
) : DependencyContainer {

    override fun module(className: Class<out ViewModel>) =
        if (className == NavigationViewModel.Base::class.java)
            NavigationModule(BaseRouter())
        else
            dependencyContainer.module(className)
}