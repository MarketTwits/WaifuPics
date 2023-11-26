package com.markettwits.waifupics.core

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.markettwits.core.Core
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.wrappers.SaveAndRestoreState
import com.markettwits.di.GalleryDependencyContainer
import com.markettwits.di.NavigationDependencyContainer
import com.markettwits.random_image.di.RandomImageDependencyContainer
import com.markettwits.waifupics.navigation.BaseRouter

interface MakeDependencies {
    fun dependencies(): DependencyContainer
    fun navigation() : BaseRouter

    class Base(
        private val context: Context,
        private val saveAndRestoreState: SaveAndRestoreState,
    ) : MakeDependencies {
        override fun dependencies(): DependencyContainer {
            val core = Core(context, saveAndRestoreState)
            val error = DependencyContainer.Error()
            val gallery = GalleryDependencyContainer(navigation(),error, saveAndRestoreState, core,)
            val randomImage = RandomImageDependencyContainer(core, gallery)
            val navigation = NavigationDependencyContainer(randomImage, navigation())
            return BaseDependencyContainer(core, navigation)
        }
        override fun navigation() = BaseRouter()
    }
}