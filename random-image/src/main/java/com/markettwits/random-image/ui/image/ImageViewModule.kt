package com.markettwits.`random-image`.ui.image

import com.markettwits.core.Core
import com.markettwits.core.sl.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.waifupics.view.main.data.ImageLoader
import com.markettwits.waifupics.view.main.data.RandomImageRepository
import com.markettwits.waifupics.view.main.data.RandomImageUiMapper
import com.markettwits.waifupics.view.main.data.net.MakeService
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel
import com.markettwits.waifupics.view.main.ui.image.RandomImageCommunication

class ImageViewModule(
    private val core: Core,
    private val filter : FilterCommunication
) : Module<ImageViewModel> {
    override fun viewModel() =
        ImageViewModel(
            filter,
            AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
            RandomImageCommunication.Base(),
            RandomImageRepository.Base(
                ImageLoader.Base(core.context()),
                MakeService.Base(),
                RandomImageUiMapper.Base()
            )
        )
}