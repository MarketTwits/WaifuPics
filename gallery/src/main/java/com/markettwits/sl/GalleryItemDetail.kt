package com.markettwits.sl

import com.markettwits.core.sl.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.list.DetailCommunication
import com.markettwits.presentation.detail.GalleryScreenViewModel

class GalleryItemDetail(private val communication: DetailCommunication, private val repository: GalleryRepository) : Module<GalleryScreenViewModel.Base> {

    override fun viewModel(): GalleryScreenViewModel.Base {
        return GalleryScreenViewModel.Base(communication, AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())), repository)
    }
}