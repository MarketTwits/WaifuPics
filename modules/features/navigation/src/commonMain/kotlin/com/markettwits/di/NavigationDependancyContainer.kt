package com.markettwits.di

import androidx.lifecycle.ViewModel
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.di.Module
import com.markettwits.presentation.NavigationRouter
import com.markettwits.presentation.NavigationViewModel
import kotlin.reflect.KClass

class NavigationDependencyContainer(
    private val dependencyContainer: DependencyContainer,
    private val navigation : NavigationRouter,
): DependencyContainer {

    override fun <T : Any> module(className: KClass<out T>): Module<ViewModel> =
        if (className == NavigationViewModel::class)
            NavigationModule(navigation)
        else
            dependencyContainer.module(className)
}