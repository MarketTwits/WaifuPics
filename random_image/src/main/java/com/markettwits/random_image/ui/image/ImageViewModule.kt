package com.markettwits.random_image.ui.image

import com.markettwits.core.Core
import com.markettwits.core.RealmDatabaseProvider
import com.markettwits.core.sl.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.ImageRepository
import com.markettwits.data.mapper.ImageUiToCacheMapper
import com.markettwits.data.store.ImageLoaderDataSource
import com.markettwits.data.store.ImagesCacheDataSource
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.random_image.data.RandomImageRepository
import com.markettwits.random_image.data.RandomImageUiMapper
import com.markettwits.random_image.ui.ImageViewModel
import com.markettwits.random_image.ui.RandomImageCommunication
import com.markettwits.random_image.ui.bottom_pannel.share.ShareImage
import com.markettwits.waifupics.view.main.data.ImageLoader
import com.markettwits.waifupics.view.main.data.net.MakeService

class ImageViewModule(
    private val core: Core,
    private val filter: FilterCommunication
) : Module<ImageViewModel.Base> {
    //FIXME
    private val repository =
        ImageRepository.Base(
            ImagesCacheDataSource(RealmDatabaseProvider.Base()),
            ImageUiToCacheMapper.Base(),
            ImageLoaderDataSource.Base(core.context())
        )

    override fun viewModel() =
        ImageViewModel.Base(
            filter,
            AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
            RandomImageCommunication.Base(),
            RandomImageRepository.Base(
                ImageLoader.Base(core.context()),
                MakeService.Base(),
                RandomImageUiMapper.Base(),
                repository,
            ),
            ShareImage.Base(core.context())
        )
}