package com.markettwits.sl

import com.markettwits.core.RealmDatabaseProvider
import com.markettwits.core.sl.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.FavoriteImageCacheToUiMapper
import com.markettwits.data.GalleryRepository
import com.markettwits.data.ImageUiToCacheMapper
import com.markettwits.database.FavoriteImageRepository
import com.markettwits.database.ImagesCacheDataSource
import com.markettwits.presentation.GalleryCommunication
import com.markettwits.presentation.GalleryViewModel

class GalleryModule : Module<GalleryViewModel> {
    val repository = GalleryRepository.Base(
        FavoriteImageRepository.Base(ImagesCacheDataSource(RealmDatabaseProvider.Base()), ImageUiToCacheMapper.Base()),
        FavoriteImageCacheToUiMapper.Base(),
    )
    override fun viewModel() = GalleryViewModel(
        GalleryCommunication.Base(),
        repository,
        AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base()))
    )
}