package com.markettwits.waifupics.core.sl

import androidx.lifecycle.ViewModel
import com.markettwits.waifupics.core.Core
import com.markettwits.waifupics.view.navigation.NavigationModule
import com.markettwits.waifupics.view.navigation.view.NavigationViewModel

interface DependencyContainer {
    fun module(className: Class<out ViewModel>): Module<out ViewModel>
    class Error : DependencyContainer {
        override fun module(className: Class<out ViewModel>): Module<out ViewModel> {
            throw IllegalArgumentException("unknown className $className")
        }
    }

    class Base(
        private val core: Core,
        private val dependencyContainer: DependencyContainer = Error()
    ) : DependencyContainer {
        override fun module(className: Class<out ViewModel>) = when (className) {
            NavigationViewModel::class.java -> NavigationModule()
            else -> dependencyContainer.module(className)
        }
    }
}