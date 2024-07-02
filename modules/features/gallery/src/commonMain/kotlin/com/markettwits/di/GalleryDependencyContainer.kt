package com.markettwits.di

import androidx.lifecycle.ViewModel
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.di.Module
import com.markettwits.core.di.PlatformDependencyContainer
import com.markettwits.data.FavoriteImageCacheToUiMapper
import com.markettwits.data.GalleryRepository
import com.markettwits.image_action.api.EmptyIntentAction
import com.markettwits.image_action.api.ImageLoader
import com.markettwits.presentation.navigation.GalleryRouter
import com.markettwits.presentation.screens.detail.GalleryScreenViewModel
import com.markettwits.presentation.screens.list.GalleryViewModel
import com.markettwits.presentation.screens.list.communication.DetailCommunication
import com.markettwits.presentation.screens.list.communication.GalleryCommunication
import kotlin.reflect.KClass

class GalleryDependencyContainer(
    private val navigation: GalleryRouter,
    private val dependencyContainer: DependencyContainer,
    private val platformDependencyContainer: PlatformDependencyContainer
) : DependencyContainer {

    private val communication = DetailCommunication.Base()
    private val galleryCommunication = GalleryCommunication.Base()

    private val imageLoader = object : ImageLoader {

        override suspend fun saveToGallery(imageUrl: String) {

        }
    }

    private val repository = GalleryRepository.Base(
        dataSource = CacheDataSourceModule.provideImageRepository(),
        cacheToUiMapper = FavoriteImageCacheToUiMapper.Base(),
        imageLoader = imageLoader,
    )

    private val imageIntentAction = EmptyIntentAction()

    override fun <T : Any> module(className: KClass<out T>): Module<ViewModel> = when (className) {
        GalleryViewModel.Base::class -> GalleryScreenModule(
            communication = communication,
            repository = repository,
            galleryCommunication = galleryCommunication,
            navigation = navigation,
            imageIntentAction = imageIntentAction
        )

        GalleryScreenViewModel.Base::class -> GalleryItemDetailModule(
            communication = communication,
            galleryCommunication = galleryCommunication,
            repository = repository,
            navigation = navigation,
            image = imageIntentAction
        )

        else -> dependencyContainer.module(className)
    }
}