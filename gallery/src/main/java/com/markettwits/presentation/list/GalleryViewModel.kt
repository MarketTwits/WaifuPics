package com.markettwits.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.core.communication.Communication
import com.markettwits.core.communication.SingleLiveEvent
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.detail.ImageFavoriteUi
import com.markettwits.presentation.list.allert_dialog.ProtectedUiState
import com.markettwits.presentation.list.allert_dialog.ProtectedUiStateEvent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface GalleryViewModel{
  // fun observeProtected() : StateFlow<ProtectedUiState>
    fun observeProtectedEvent() : LiveData<ProtectedUiStateEvent>
    fun toDetail(state : ImageFavoriteUi)
    fun favoriteImages()
    fun deleteImage(item : String, id : Long)

    class Base(
        private val protectedEventCommunication: SingleLiveEvent<ProtectedUiStateEvent>,
     //   private val protected : ProtectedStateCommunication,
        private val gallery : GalleryCommunication,
        private val repository: GalleryRepository,
        private val async: AsyncViewModel.Abstract<List<ImageFavoriteUi>>,
        private val current : DetailCommunication,
        private val protectedToBaseUiMapper: ProtectedToBaseUiMapper
    ) : ViewModel(), StateCommunication.State<List<ImageFavoriteUi>>, GalleryViewModel {

        init {
            favoriteImages()
        }

        override fun observeProtectedEvent() = protectedEventCommunication

        override fun toDetail(state : ImageFavoriteUi){
            current.map(state)
        }
        override fun favoriteImages() {
            viewModelScope.launch {
                repository.observe().collect{
                    gallery.map(it)
                }
            }
        }

        override fun deleteImage(item : String, id : Long){
            async.handleAsync({
                repository.delete(item, id)
            }){
                favoriteImages()
            }
        }


        override fun state() = gallery.state()
    }
}

interface GalleryCommunication : StateCommunication.Mutable<List<ImageFavoriteUi>>{
    class Base : StateCommunication.Abstract<List<ImageFavoriteUi>>(listOf()), GalleryCommunication
}

interface DetailCommunication : StateCommunication.Mutable<ImageFavoriteUi>{
    class Base : StateCommunication.Abstract<ImageFavoriteUi>(ImageFavoriteUi.Initial), DetailCommunication
}
//interface ProtectedCommunication : StateCommunication.Mutable<ProtectedUiState>{
//    class Base : StateCommunication.Abstract<ProtectedUiState>(ProtectedUiState.Success), ProtectedCommunication
//}
interface ProtectedEventCommunication : Communication.Mutable<ProtectedUiStateEvent>{
    class Base : Communication.SingleUiUpdate<ProtectedUiStateEvent>(), ProtectedEventCommunication
}
interface ProtectedStateCommunication : StateCommunication.Mutable<ProtectedUiState>{
    class Base : StateCommunication.Abstract<ProtectedUiState>(ProtectedUiState.Hide), ProtectedStateCommunication
}
