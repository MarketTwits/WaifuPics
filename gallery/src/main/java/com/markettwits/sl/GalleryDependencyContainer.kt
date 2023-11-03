package com.markettwits.sl

import androidx.lifecycle.ViewModel
import com.markettwits.core.Core
import com.markettwits.core.RealmDatabaseProvider
import com.markettwits.core.sl.DependencyContainer
import com.markettwits.data.FavoriteImageCacheToUiMapper
import com.markettwits.data.FavoriteImageRepository
import com.markettwits.data.GalleryRepository
import com.markettwits.data.ImageLoaderDataSource
import com.markettwits.data.ImageUiToCacheMapper
import com.markettwits.data.ImagesCacheDataSource
import com.markettwits.presentation.list.DetailCommunication
import com.markettwits.presentation.list.GalleryScreenViewModel
import com.markettwits.presentation.list.GalleryViewModel

class GalleryDependencyContainer(
    private val dependencyContainer: DependencyContainer,
    private val core: Core
) : DependencyContainer {
    private val communication = DetailCommunication.Base()
    private val repository = GalleryRepository.Base(
        FavoriteImageRepository.Base(
            ImagesCacheDataSource(RealmDatabaseProvider.Base()),
            ImageUiToCacheMapper.Base(),
            ImageLoaderDataSource.Base(core.context())
        ),
        FavoriteImageCacheToUiMapper.Base(),
    )

    override fun module(className: Class<out ViewModel>) = when (className) {
        GalleryViewModel::class.java -> GalleryModule(communication, repository)
        GalleryScreenViewModel.Base::class.java -> GalleryItemDetail(communication, repository)
        else -> dependencyContainer.module(className)
    }
}