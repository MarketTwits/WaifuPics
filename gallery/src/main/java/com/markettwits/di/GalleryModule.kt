package com.markettwits.di

import com.markettwits.core.sl.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.list.DetailCommunication
import com.markettwits.presentation.list.GalleryCommunication
import com.markettwits.presentation.list.GalleryViewModel

class GalleryModule(
    private val communication: DetailCommunication,
    private val repository: GalleryRepository,
    private val galleryCommunication: GalleryCommunication
) : Module<GalleryViewModel.Base> {
    override fun viewModel() = GalleryViewModel.Base(
        galleryCommunication,
        repository,
        AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
        communication,
    )
}