package com.markettwits.core

import androidx.lifecycle.ViewModel
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.di.Module
import com.markettwits.di.NavigationModule
import com.markettwits.navigation.BaseRouter
import com.markettwits.presentation.NavigationViewModel
import kotlin.reflect.KClass

class BaseDependencyContainer(
    private val dependencyContainer: DependencyContainer,
) : DependencyContainer {

    override fun <T : Any> module(className:  KClass<out T>): Module<ViewModel> =
        if (className == NavigationViewModel.Base::class)
            NavigationModule(BaseRouter())
        else
            dependencyContainer.module(className)
}