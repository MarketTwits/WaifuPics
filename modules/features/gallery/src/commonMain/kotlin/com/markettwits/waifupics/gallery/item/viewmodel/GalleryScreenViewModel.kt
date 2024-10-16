package com.markettwits.waifupics.gallery.item.viewmodel

import androidx.lifecycle.ViewModel
import com.markettwits.async.AsyncViewModel
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.waifupics.gallery.common.GalleryRepository
import com.markettwits.waifupics.gallery.items.components.communication.DetailCommunication
import com.markettwits.waifupics.gallery.items.components.communication.GalleryCommunication
import com.markettwits.waifupics.gallery.items.components.communication.GalleryScreenLabelsCommunication
import com.markettwits.waifupics.gallery.items.components.copy.SystemService
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUiState
import kotlinx.coroutines.flow.StateFlow

interface GalleryScreenViewModel {

    fun onClickDelete()

    fun onClickSetImageAs()

    fun onClickSaveToGallery()

    fun onClickShareImage()

    fun onClickCopy(text: String)

    fun currentImage(): StateFlow<ImageFavoriteUi>

    fun setCurrentItem(index: Int)

    fun state(): StateFlow<ImageFavoriteUiState>

    val labels: StateFlow<Labels>

    sealed interface Labels {
        data object GoBack : Labels

        data object Empty : Labels
    }

    class Base(
        private val itemCommunication: DetailCommunication,
        private val listCommunication: GalleryCommunication,
        private val labelsCommunication: GalleryScreenLabelsCommunication,
        private val async: AsyncViewModel<Unit>,
        private val repository: GalleryRepository,
        private val imageIntentAction: ImageIntentAction.Mutable,
        private val systemService: SystemService,
    ) : ViewModel(), GalleryScreenViewModel {

        override val labels: StateFlow<Labels> = labelsCommunication.state()

        override fun state(): StateFlow<ImageFavoriteUiState> = listCommunication.state()

        override fun currentImage(): StateFlow<ImageFavoriteUi> = itemCommunication.state()

        override fun onClickCopy(text: String) {
            systemService.copy(text)
        }

        override fun onClickDelete() {
            async.handleAsyncSingle {
                repository.delete(currentImage().value.id)
            }
        }

        override fun onClickSetImageAs() {
            async.handleAsyncSingle {
                imageIntentAction.launchUseAs(currentImage().value.imageUrl)
            }
        }

        override fun onClickSaveToGallery() {
            async.handleAsyncSingle {
                repository.saveToGallery(currentImage().value.imageUrl)
            }
        }

        override fun onClickShareImage() {
            async.handleAsyncSingle {
                imageIntentAction.shareImage(currentImage().value.imageUrl)
            }
        }

        override fun setCurrentItem(index: Int) {
            if (index < 0 || state().value.items.isEmpty()) {
                labelsCommunication.map(Labels.GoBack)
            } else {
                itemCommunication.map(state().value.items[index])
            }
        }

    }
}