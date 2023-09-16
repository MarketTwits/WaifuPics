package com.markettwits.waifupics.core

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.waifupics.view.navigation.NavigationModule
import com.markettwits.waifupics.view.navigation.view.NavigationViewModel

class BaseDependencyContainer(
    private val core: Core,
    private val dependencyContainer: DependencyContainer,
) : DependencyContainer {

    override fun module(className: Class<out ViewModel>) =
        if (className == NavigationViewModel::class.java)
            NavigationModule()
        else
            dependencyContainer.module(className)
}