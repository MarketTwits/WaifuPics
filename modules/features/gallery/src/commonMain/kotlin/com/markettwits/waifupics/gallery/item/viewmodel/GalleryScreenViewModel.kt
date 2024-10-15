package com.markettwits.waifupics.gallery.item.viewmodel

import androidx.lifecycle.ViewModel
import com.markettwits.async.communication.StateCommunication
import com.markettwits.async.wrappers.AsyncViewModel
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.waifupics.gallery.common.GalleryRepository
import com.markettwits.waifupics.gallery.items.components.copy.SystemService
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUiState
import com.markettwits.waifupics.gallery.items.components.communication.DetailCommunication
import com.markettwits.waifupics.gallery.items.components.communication.GalleryCommunication
import kotlinx.coroutines.flow.StateFlow

interface GalleryScreenViewModel {

    fun onClickDelete()

    fun onClickEditImage()

    fun onClickSetImageAs()

    fun onClickSaveToGallery()

    fun onClickShareImage()

    fun onClickCopy(text: String)

    fun currentImage(): StateFlow<ImageFavoriteUi>

    fun setCurrentItem(index: Int)

    fun state(): StateFlow<ImageFavoriteUiState>

    class Base(
        private val item: DetailCommunication,
        private val list: GalleryCommunication,
        private val async: AsyncViewModel<Unit>,
        private val repository: GalleryRepository,
        private val imageIntentAction: ImageIntentAction.Mutable,
        private val systemService: SystemService,
    ) : ViewModel(), GalleryScreenViewModel {

        override fun state() = list.state()

        override fun currentImage(): StateFlow<ImageFavoriteUi> = item.state()

        override fun onClickCopy(text: String) {
            systemService.copy(text)
        }

        override fun onClickDelete() {
            async.handleAsyncSingle {
                repository.delete(item.state().value.id)
            }
        }

        override fun onClickEditImage() {
            async.handleAsyncSingle {
                imageIntentAction.launchOpenWith(item.state().value.imageUrl)
            }
        }

        override fun onClickSetImageAs() {
            async.handleAsyncSingle {
                imageIntentAction.launchUseAs(item.state().value.imageUrl)
            }
        }

        override fun onClickSaveToGallery() {
           async.handleAsyncSingle {
               repository.saveToGallery(item.state().value.imageUrl)
           }
        }

        override fun onClickShareImage() {
            async.handleAsyncSingle {
                imageIntentAction.shareImage(item.state().value.imageUrl)
            }
        }

        override fun setCurrentItem(index: Int) {
            if (index < 0 || list.state().value.items.isEmpty()) {
               // onClickPop()
               //TODO create pop action when image in stack was empty
            } else {
                item.map(list.state().value.items[index])
            }
        }

    }
}