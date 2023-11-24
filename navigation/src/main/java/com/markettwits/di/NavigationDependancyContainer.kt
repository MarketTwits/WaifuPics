package com.markettwits.di

import androidx.lifecycle.ViewModel
import com.markettwits.core.di.DependencyContainer
import com.markettwits.presentation.HandleNavigation
import com.markettwits.presentation.NavigationViewModel

class NavigationDependencyContainer(
    private val dependencyContainer: DependencyContainer,
    private val navigation : HandleNavigation,
): DependencyContainer {
    override fun module(className: Class<out ViewModel>) =
        if (className == NavigationViewModel::class.java)
            NavigationModule(navigation)
        else
            dependencyContainer.module(className)
}