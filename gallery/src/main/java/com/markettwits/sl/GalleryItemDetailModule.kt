package com.markettwits.sl

import com.markettwits.core.Core
import com.markettwits.core.sl.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.core_ui.image.ShareImage
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.list.DetailCommunication
import com.markettwits.presentation.detail.GalleryScreenViewModel
import com.markettwits.presentation.detail.ImageControllerPanel
import com.markettwits.presentation.detail.ImageControllerPanelCommunication
import com.markettwits.presentation.list.GalleryCommunication

class GalleryItemDetailModule(
    private val core : Core,
    private val communication: DetailCommunication,
    private val galleryCommunication: GalleryCommunication,
    private val repository: GalleryRepository,
) : Module<GalleryScreenViewModel.Base> {

    override fun viewModel(): GalleryScreenViewModel.Base {
        return GalleryScreenViewModel.Base(
            communication,
            galleryCommunication,
            AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
            repository,
            ImageControllerPanel.Base(
                DispatchersList.Base(),
                ImageControllerPanelCommunication.Base()
            ),
            ShareImage.Base(core.context())
        )
    }
}