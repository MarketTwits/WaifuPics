package com.markettwits.waifupics.view.main.ui.image

import com.markettwits.waifupics.core.sl.Module
import com.markettwits.waifupics.view.main.data.net.MakeService

class ImageViewModule : Module<ImageViewModel> {
    override fun viewModel() =
        ImageViewModel(ImageCommunication.Base(), MakeService.Base().service())
}