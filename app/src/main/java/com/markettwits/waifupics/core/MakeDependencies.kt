package com.markettwits.waifupics.core

import android.content.Context
import com.markettwits.core.Core
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.core.wrappers.SaveAndRestoreState

interface MakeDependencies {
    fun dependencies(): DependencyContainer

    class Base(
        private val context: Context,
        private val saveAndRestoreState: SaveAndRestoreState,
    ) : MakeDependencies {
        override fun dependencies(): DependencyContainer {
            val core = Core(context, saveAndRestoreState)
            val error = DependencyContainer.Error()
            val main = MainDependencyContainer(core, error)
            return BaseDependencyContainer(core, main)
        }
    }
}