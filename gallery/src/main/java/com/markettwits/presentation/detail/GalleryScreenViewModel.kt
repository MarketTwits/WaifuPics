package com.markettwits.presentation.detail

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.list.DetailCommunication
import com.markettwits.presentation.list.GalleryCommunication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

interface GalleryScreenViewModel {
    fun activePanel()
    fun imagePanelState(): StateFlow<ImageControllerState>
    fun initScreen()
    fun setImageAs()
    fun delete()
    fun infoAboutImage()
    fun setCurrentItem(image: ImageFavoriteUi)
    fun saveToGallery()
    fun currentImage() : StateFlow<ImageFavoriteUi>
    fun state(): StateFlow<List<ImageFavoriteUi>>

    class Base(
        private val item: DetailCommunication,
        private val list: GalleryCommunication,
        private val async: AsyncViewModel<Unit>,
        private val repository: GalleryRepository,
        private val imageControllerPanel: ImageControllerPanel,
    ) : ViewModel(), GalleryScreenViewModel {
        override fun state() = list.state()
        override fun activePanel() {
            async.handleAsync({ imageControllerPanel.changeState() }) {}
        }

        override fun imagePanelState(): StateFlow<ImageControllerState> {
            return imageControllerPanel.state().state()
        }

        override fun initScreen() {
            async.handleAsync({
                imageControllerPanel.init()
            }) {}
        }

        override fun setImageAs() {

        }

        override fun delete() {
            async.handleAsync({
                repository.delete(item.state().value.imageUrl(), item.state().value.id())
            }) {}
        }

        override fun infoAboutImage() {

        }

        override fun setCurrentItem(image: ImageFavoriteUi) {
            item.map(image)
        }


        override fun saveToGallery() {
            async.handleAsync({
                repository.saveToGallery(item.state().value.imageUrl())
            }) {}
        }

        override fun currentImage() =
            item.state()

    }
}
interface CurrentItemCommunication : StateCommunication.Mutable<ImageFavoriteUi>{
    class Base : StateCommunication.Abstract<ImageFavoriteUi>(ImageFavoriteUi.Initial),  CurrentItemCommunication
}
