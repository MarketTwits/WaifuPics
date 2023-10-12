package com.markettwits.presentation

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import kotlinx.coroutines.flow.MutableStateFlow

class GalleryViewModel(
    private val communication : GalleryCommunication,
    private val repository: GalleryRepository,
    private val async: AsyncViewModel.Abstract<List<ImageFavoriteUi>>,
) : ViewModel(), StateCommunication.State<List<ImageFavoriteUi>> {
    val screenState = MutableStateFlow<GalleryUiState>(GalleryUiState.Initial)

    init {
        favoriteImages()
        screenState.value = GalleryUiState.Initial
    }

    fun observeState(){
        if (screenState.value is GalleryUiState.Selected){
            val items = communication.state().value.filter { it is ImageFavoriteUi.Selected }
           // if ()
        }
    }

    fun favoriteImages() {
       async.handleAsync({
          repository.fetch()
       }){ communication.map(it) }
    }
    fun deleteImage(item : ImageFavoriteUi){
        async.handleAsync({
            //repository.delete(item)
        }){favoriteImages()}
    }
    fun select(item : ImageFavoriteUi){
        val updatedList = communication.state().value.toMutableList()
        val selectedIndex = updatedList.indexOf(item)
        if (selectedIndex != -1) {
            val selectedVersion = item.changeState()
            updatedList[selectedIndex] = selectedVersion
            communication.map(updatedList)
        }
    }
    override fun state() = communication.state()
}
interface GalleryCommunication : StateCommunication.Mutable<List<ImageFavoriteUi>>{
    class Base : StateCommunication.Abstract<List<ImageFavoriteUi>>(listOf()), GalleryCommunication
}