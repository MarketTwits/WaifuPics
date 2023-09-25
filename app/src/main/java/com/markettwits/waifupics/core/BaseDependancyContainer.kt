package com.markettwits.waifupics.core

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.presentation.NavigationViewModel
import com.markettwits.sl.NavigationModule

class BaseDependencyContainer(
    private val core: Core,
    private val dependencyContainer: DependencyContainer,
) : DependencyContainer {

    override fun module(className: Class<out ViewModel>) =
        if (className == NavigationViewModel::class.java)
            NavigationModule(BaseHandleNavigation())
        else
            dependencyContainer.module(className)
}