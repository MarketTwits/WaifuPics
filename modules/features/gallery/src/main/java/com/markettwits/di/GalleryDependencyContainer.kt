package com.markettwits.di

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.RealmDatabaseProvider
import com.markettwits.core.di.DependencyContainer
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.SaveAndRestoreState
import com.markettwits.data.FavoriteImageCacheToUiMapper
import com.markettwits.data.GalleryRepository
import com.markettwits.data.ImageRepository
import com.markettwits.data.mapper.ImageUiToCacheMapper
import com.markettwits.data.media_info.ExifServiceWrapper
import com.markettwits.data.media_info.ImageInfoToUiMapper
import com.markettwits.data.store.ImageLoaderDataSource
import com.markettwits.data.store.ImagesCacheDataSource
import com.markettwits.image_action.impl.ImageFileWrapper
import com.markettwits.image_action.impl.ImageIntentActionImpl
import com.markettwits.presentation.navigation.GalleryRouter
import com.markettwits.presentation.screens.detail.GalleryScreenViewModel
import com.markettwits.presentation.screens.list.communication.DetailCommunication
import com.markettwits.presentation.screens.list.communication.GalleryCommunication
import com.markettwits.presentation.screens.list.GalleryViewModel

class GalleryDependencyContainer(
    private val navigation: GalleryRouter,
    private val dependencyContainer: DependencyContainer,
    private val saveAndRestoreState: SaveAndRestoreState,
    private val core: Core
) : DependencyContainer {
    private val communication = DetailCommunication.Base()
    private val galleryCommunication = GalleryCommunication.Base()

    private val repository = GalleryRepository.Base(
        ImageRepository.Base(
            ImagesCacheDataSource(RealmDatabaseProvider.Base()),
            ImageUiToCacheMapper.Base(),
            ImageLoaderDataSource.Base(core.context(), DispatchersList.Base())
        ),
        FavoriteImageCacheToUiMapper.Base(),
        ExifServiceWrapper.Base(),
        ImageInfoToUiMapper.Base()
    )
    private val imageIntentAction = ImageIntentActionImpl(core.context(), ImageFileWrapper.Base(core.context()))

    override fun module(className: Class<out ViewModel>) = when (className) {
        GalleryViewModel.Base::class.java -> GalleryScreenModule(
            communication,
            repository,
            galleryCommunication,
            navigation,
            imageIntentAction
        )

        GalleryScreenViewModel.Base::class.java -> GalleryItemDetailModule(
            core,
            communication,
            galleryCommunication,
            repository,
            navigation,
            imageIntentAction
        )

        else -> dependencyContainer.module(className)
    }
}