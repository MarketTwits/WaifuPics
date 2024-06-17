package com.markettwits.waifupics.core

import android.content.Context
import com.markettwits.core.Core
import com.markettwits.core.di.DependencyContainer
import com.markettwits.di.GalleryDependencyContainer
import com.markettwits.di.NavigationDependencyContainer
import com.markettwits.random_image.di.RandomImageDependencyContainer
import com.markettwits.waifupics.navigation.BaseRouter

interface MakeDependencies {

    fun dependencies(): DependencyContainer

    fun navigation() : BaseRouter

    class Base(
        private val context: Context,
    ) : MakeDependencies {
        override fun dependencies(): DependencyContainer {
            val core = Core(context)
            val error = DependencyContainer.Error()
            val gallery = GalleryDependencyContainer(navigation(),error, core)
            val randomImage = RandomImageDependencyContainer(gallery)
            val navigation = NavigationDependencyContainer(randomImage, navigation())
            return BaseDependencyContainer(navigation)
        }

        override fun navigation() = BaseRouter()
    }
}