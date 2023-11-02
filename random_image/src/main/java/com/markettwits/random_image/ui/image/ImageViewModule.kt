package com.markettwits.random_image.ui.image

import com.markettwits.core.Core
import com.markettwits.core.RealmDatabaseProvider
import com.markettwits.core.sl.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.FavoriteImageRepository
import com.markettwits.data.ImageUiToCacheMapper
import com.markettwits.data.ImagesCacheDataSource
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.random_image.data.RandomImageRepository
import com.markettwits.random_image.data.RandomImageUiMapper
import com.markettwits.random_image.ui.ImageViewModel
import com.markettwits.random_image.ui.RandomImageCommunication
import com.markettwits.waifupics.view.main.data.ImageLoader
import com.markettwits.waifupics.view.main.data.net.MakeService

class ImageViewModule(
    private val core: Core,
    private val filter : FilterCommunication
) : Module<ImageViewModel> {
    //FIXME
    private val repository =
        FavoriteImageRepository.Base(ImagesCacheDataSource(RealmDatabaseProvider.Base()), ImageUiToCacheMapper.Base())
    override fun viewModel() =
        ImageViewModel(
            filter,
            AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
            RandomImageCommunication.Base(),
            RandomImageRepository.Base(
                ImageLoader.Base(core.context()),
                MakeService.Base(),
                RandomImageUiMapper.Base()
            ),
            repository
        )
}