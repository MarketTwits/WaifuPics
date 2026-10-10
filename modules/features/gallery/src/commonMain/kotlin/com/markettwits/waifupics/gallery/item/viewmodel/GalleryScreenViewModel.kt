package com.markettwits.waifupics.gallery.item.viewmodel

import androidx.lifecycle.ViewModel
import com.markettwits.async.AsyncViewModel
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.waifupics.gallery.common.GalleryRepository
import com.markettwits.waifupics.gallery.common.GalleryDeletion
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
        private val deletion: GalleryDeletion,
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
            val image = currentImage().value
            async.handleAsyncSingle {
                deletion.delete(setOf(image.id), viewerFocus = image.id)
            }
        }

        override fun onClickSetImageAs() {
            val image = currentImage().value
            async.handleAsyncSingle {
                imageIntentAction.launchUseAs(image.imageUrl)
            }
        }

        override fun onClickSaveToGallery() {
            val image = currentImage().value
            async.handleAsyncSingle {
                repository.saveToGallery(image.imageUrl)
            }
        }

        override fun onClickShareImage() {
            val image = currentImage().value
            async.handleAsyncSingle {
                imageIntentAction.shareImage(image.imageUrl)
            }
        }

        override fun setCurrentItem(index: Int) {
            val image = state().value.items.filterIsInstance<ImageFavoriteUi.Base>().getOrNull(index) ?: return
            labelsCommunication.map(Labels.Empty)
            itemCommunication.map(image)
        }

    }
}
