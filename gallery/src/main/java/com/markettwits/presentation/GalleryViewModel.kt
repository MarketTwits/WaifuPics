package com.markettwits.presentation

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository

class GalleryViewModel(
    private val communication : GalleryCommunication,
    private val repository: GalleryRepository,
    private val async: AsyncViewModel.Abstract<List<ImageFavoriteUi>>
) : ViewModel(), StateCommunication.State<List<ImageFavoriteUi>> {
    init {
        favoriteImages()
    }
    fun favoriteImages() {
       async.handleAsync({
          repository.fetch()
       }){ communication.map(it) }
    }

    override fun state() = communication.state()
}
interface GalleryCommunication : StateCommunication.Mutable<List<ImageFavoriteUi>>{
    class Base : StateCommunication.Abstract<List<ImageFavoriteUi>>(emptyList()), GalleryCommunication
}