package com.markettwits.sl

import com.markettwits.core.communication.SingleLiveEvent
import com.markettwits.core.sl.Module
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core.wrappers.DispatchersList
import com.markettwits.core.wrappers.RunAsync
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.list.DetailCommunication
import com.markettwits.presentation.list.GalleryCommunication
import com.markettwits.presentation.list.GalleryViewModel
import com.markettwits.presentation.list.ProtectedStateCommunication
import com.markettwits.presentation.list.ProtectedToBaseUiMapper
import com.markettwits.presentation.list.allert_dialog.ProtectedUiState
import com.markettwits.presentation.list.allert_dialog.ProtectedUiStateEvent

class GalleryModule(
    private val communication: DetailCommunication,
    private val repository: GalleryRepository,
    private val galleryCommunication: GalleryCommunication
) : Module<GalleryViewModel.Base> {
    override fun viewModel() = GalleryViewModel.Base(
       SingleLiveEvent<ProtectedUiStateEvent>(),
      //  ProtectedStateCommunication.Base(),
        galleryCommunication,
        repository,
        AsyncViewModel.Base(RunAsync.Base(DispatchersList.Base())),
        communication,
        ProtectedToBaseUiMapper.Base()
    )
}