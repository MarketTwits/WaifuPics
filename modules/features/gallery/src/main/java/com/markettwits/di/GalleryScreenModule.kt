package com.markettwits.di

import com.markettwits.core.di.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.navigation.GalleryRouter
import com.markettwits.presentation.screens.list.communication.DetailCommunication
import com.markettwits.presentation.screens.list.communication.GalleryCommunication
import com.markettwits.presentation.screens.list.GalleryViewModel
import com.markettwits.presentation.screens.list.communication.SelectedImageCommunication
import com.markettwits.presentation.screens.list.communication.SelectedModeCommunication

class GalleryScreenModule(
    private val communication: DetailCommunication,
    private val repository: GalleryRepository,
    private val galleryCommunication: GalleryCommunication,
    private val navigation: GalleryRouter,
) : Module<GalleryViewModel.Base> {
    override fun viewModel() = GalleryViewModel.Base(
        galleryCommunication,
        repository,
        AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
        communication,
        SelectedModeCommunication.Base(),
        navigation,
        SelectedImageCommunication.Base()
    )
}