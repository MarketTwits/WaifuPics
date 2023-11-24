package com.markettwits.waifupics.core

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.di.Module

class MainDependencyContainer(
    private val core: Core,
    private val other: DependencyContainer
) : DependencyContainer {
    val navigation = BaseHandleNavigation()
    override fun module(className: Class<out ViewModel>): Module<out ViewModel> = when (className) {
        else -> other.module(className)
    }
}


