package com.markettwits.di

import com.markettwits.core.di.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.navigation.GalleryRouter
import com.markettwits.presentation.screens.list.DetailCommunication
import com.markettwits.presentation.screens.list.GalleryCommunication
import com.markettwits.presentation.screens.list.GalleryViewModel
import com.markettwits.presentation.screens.list.SelectedImageCommunication

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
        SelectedImageCommunication.Base(),
        navigation
    )
}