package com.markettwits.waifupics.core

import android.content.Context
import com.markettwits.core.Core
import com.markettwits.core.sl.DependencyContainer

interface MakeDependencies {
    fun dependencies(): DependencyContainer

    class Base(private val context: Context) : MakeDependencies {
        override fun dependencies(): DependencyContainer {
            val core = Core(context)
            val error = DependencyContainer.Error()
            val main = MainDependencyContainer(core, error)
            return BaseDependencyContainer(core, main)
        }
    }
}