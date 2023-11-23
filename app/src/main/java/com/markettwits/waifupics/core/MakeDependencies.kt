package com.markettwits.waifupics.core

import android.content.Context
import com.markettwits.core.Core
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.core.wrappers.SaveAndRestoreState
import com.markettwits.di.GalleryDependencyContainer
import com.markettwits.di.NavigationDependencyContainer
import com.markettwits.random_image.di.RandomImageDependencyContainer

interface MakeDependencies {
    fun dependencies(): DependencyContainer

    class Base(
        private val context: Context,
        private val saveAndRestoreState: SaveAndRestoreState,
    ) : MakeDependencies {
        override fun dependencies(): DependencyContainer {
            val core = Core(context, saveAndRestoreState)
            val error = DependencyContainer.Error()
            val gallery = GalleryDependencyContainer(error, core)
            val randomImage = RandomImageDependencyContainer(core, gallery)
            val navigation = NavigationDependencyContainer(randomImage, BaseHandleNavigation())
            return BaseDependencyContainer(core, navigation)
        }
    }
}