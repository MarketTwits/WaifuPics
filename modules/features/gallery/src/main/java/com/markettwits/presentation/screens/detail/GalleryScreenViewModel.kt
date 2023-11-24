package com.markettwits.presentation.screens.detail

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.presentation.navigation.GalleryNavigation
import com.markettwits.presentation.screens.detail.info.MediaInfoUiState
import com.markettwits.presentation.screens.list.DetailCommunication
import com.markettwits.presentation.screens.list.GalleryCommunication
import kotlinx.coroutines.flow.StateFlow

interface GalleryScreenViewModel {

    fun setImageAs()
    fun delete()
    fun infoAboutImage(): MediaInfoUiState
    fun setCurrentItem(image: ImageFavoriteUi)
    fun saveToGallery()
    fun currentImage(): StateFlow<ImageFavoriteUi>
    fun state(): StateFlow<List<ImageFavoriteUi>>
    fun shareImage()
    fun editImage()
    fun pop()


    class Base(
        private val item: DetailCommunication,
        private val list: GalleryCommunication,
        private val async: AsyncViewModel<Unit>,
        private val repository: GalleryRepository,
        private val imageIntentAction: ImageIntentAction.Mutable,
        private val navigation: GalleryNavigation,
    ) : ViewModel(), GalleryScreenViewModel {
        override fun state() = list.state()
        override fun shareImage() {
            val imageUrl = item.state().value.imageUrl()
            async.handleAsyncSingle {
                imageIntentAction.shareImage(imageUrl)
            }
        }

        override fun editImage() {
            async.handleAsyncSingle {
                imageIntentAction.launchEditAs(item.state().value.imageUrl())
            }
        }

        override fun pop() {
            navigation.pop()
        }

        override fun setImageAs() {
            async.handleAsyncSingle{
                imageIntentAction.launchUseAs(item.state().value.imageUrl())
            }
        }

        override fun delete() {
            async.handleAsyncSingle {
                repository.delete(item.state().value.imageUrl(), item.state().value.id())
            }
        }

        override fun infoAboutImage(): MediaInfoUiState =
             repository.infoAboutImage(item.state().value.imageUrl())

        override fun setCurrentItem(image: ImageFavoriteUi) {
            item.map(image)
        }

        override fun saveToGallery() {
            async.handleAsync({
                repository.saveToGallery(item.state().value.imageUrl())
            }) {
                //TODO add callback
            }
        }

        override fun currentImage() = item.state()

    }
}

interface CurrentItemCommunication : StateCommunication.Mutable<ImageFavoriteUi> {
    class Base : StateCommunication.Abstract<ImageFavoriteUi>(ImageFavoriteUi.Initial),
        CurrentItemCommunication
}
