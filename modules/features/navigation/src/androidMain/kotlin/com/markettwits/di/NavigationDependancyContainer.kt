package com.markettwits.di

import androidx.lifecycle.ViewModel
import com.markettwits.presentation.NavigationRouter
import com.markettwits.presentation.NavigationViewModel
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.di.Module

class NavigationDependencyContainer(
    private val dependencyContainer: DependencyContainer,
    private val navigation : NavigationRouter,
): DependencyContainer {

    override fun <T> module(className: Class<out T>): Module<ViewModel> =
        if (className == NavigationViewModel::class.java)
            NavigationModule(navigation)
        else
            dependencyContainer.module(className)
}