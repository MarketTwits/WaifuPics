package com.markettwits.core

import com.markettwits.cloud_datasource.di.RandomImageDependencyContainer
import com.markettwits.core.di.DependencyContainer
import com.markettwits.di.NavigationDependencyContainer
import com.markettwits.navigation.BaseRouter

//interface MakeDependencies {
//
//    fun dependencies(): DependencyContainer
//
//    fun navigation(): BaseRouter
//
//    class Base(
//    ) :
//        MakeDependencies {
//
//        private val navigation by lazy {
//            BaseRouter()
//        }
//
//        override fun dependencies(): DependencyContainer {
//            val error = DependencyContainer.Error()
//            val gallery = GalleryDependencyContainer(navigation, error)
//            val randomImage = RandomImageDependencyContainer(gallery)
//            val navigation = NavigationDependencyContainer(randomImage, navigation)
//            return BaseDependencyContainer(navigation)
//        }
//
//        override fun navigation() = navigation
//    }
//}