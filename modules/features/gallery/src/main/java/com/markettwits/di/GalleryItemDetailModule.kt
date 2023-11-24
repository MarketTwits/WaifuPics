package com.markettwits.di

import com.markettwits.core.Core
import com.markettwits.core.di.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.GalleryRepository
import com.markettwits.image_action.ImageFileWrapper
import com.markettwits.image_action.impl.ImageIntentActionImpl
import com.markettwits.presentation.navigation.GalleryNavigation
import com.markettwits.presentation.screens.detail.GalleryScreenViewModel
import com.markettwits.presentation.screens.list.DetailCommunication
import com.markettwits.presentation.screens.list.GalleryCommunication

class GalleryItemDetailModule(
    private val core : Core,
    private val communication: DetailCommunication,
    private val galleryCommunication: GalleryCommunication,
    private val repository: GalleryRepository,
    private val navigation: GalleryNavigation
) : Module<GalleryScreenViewModel.Base> {

    override fun viewModel(): GalleryScreenViewModel.Base {
        return GalleryScreenViewModel.Base(
            communication,
            galleryCommunication,
            AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
            repository,
            ImageIntentActionImpl(core.context(), ImageFileWrapper.Base(core.context())),
            navigation
        )
    }
}