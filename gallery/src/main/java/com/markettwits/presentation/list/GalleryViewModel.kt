package com.markettwits.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.detail.ImageFavoriteUi
import kotlinx.coroutines.launch

interface GalleryViewModel{
    fun toDetail(state : ImageFavoriteUi)
    fun favoriteImages()
    fun deleteImage(item : String, id : Long)

    class Base(
        private val gallery : GalleryCommunication,
        private val repository: GalleryRepository,
        private val async: AsyncViewModel.Abstract<List<ImageFavoriteUi>>,
        private val current : DetailCommunication,
    ) : ViewModel(), StateCommunication.State<List<ImageFavoriteUi>>, GalleryViewModel {

        init {
            favoriteImages()
        }
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
