package com.markettwits.presentation.list

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.detail.ImageFavoriteUi

class GalleryViewModel(
    private val communication : GalleryCommunication,
    private val repository: GalleryRepository,
    private val async: AsyncViewModel.Abstract<List<ImageFavoriteUi>>,
    private val current : DetailCommunication
) : ViewModel(), StateCommunication.State<List<ImageFavoriteUi>> {
    val screenState = communication

    init {

      //  screenState.value = GalleryUiState.Initial
    }

//    fun observeState(){
//        if (screenState.value is GalleryUiState.Selected){
//            val items = communication.state().value.filter { it is ImageFavoriteUi.Selected }
//           // if ()
//        }
//    }
    fun toDetail(state : ImageFavoriteUi){
        current.map(state)
    }

    fun favoriteImages() {
       async.handleAsync({
          repository.fetch()
       }){ communication.map(it) }
    }
    fun deleteImage(item : String, id : Long){
        async.handleAsync({
            repository.delete(item, id)
        }){
            favoriteImages()
        }
    }
//    fun select(item : ImageFavoriteUi){
//        val updatedList = communication.state().value.toMutableList()
//        val selectedIndex = updatedList.indexOf(item)
//        if (selectedIndex != -1) {
//            val selectedVersion = item.changeState()
//            updatedList[selectedIndex] = selectedVersion
//            communication.map(updatedList)
//        }
//    }
    fun detailState() = current.state()
    override fun state() = communication.state()
}
interface GalleryCommunication : StateCommunication.Mutable<List<ImageFavoriteUi>>{
    class Base : StateCommunication.Abstract<List<ImageFavoriteUi>>(listOf()), GalleryCommunication
}
interface DetailCommunication : StateCommunication.Mutable<ImageFavoriteUi>{
    class Base : StateCommunication.Abstract<ImageFavoriteUi>(ImageFavoriteUi.Initial), DetailCommunication
}