package com.markettwits.waifupics.view.main.ui.image

import com.markettwits.core.Core
import com.markettwits.core.sl.Module
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.waifupics.view.main.data.ImageLoader
import com.markettwits.waifupics.view.main.data.RandomImageRepository
import com.markettwits.waifupics.view.main.data.RandomImageUiMapper
import com.markettwits.waifupics.view.main.data.net.MakeService

class ImageViewModule(val core: Core) : Module<ImageViewModel> {
    override fun viewModel() =
        ImageViewModel(
            DispatchersList.Base(),
            RandomImageRepository.Base(
                ImageLoader.Base(core.context()),
                MakeService.Base(),
                RandomImageUiMapper.Base()
            )
        )
}