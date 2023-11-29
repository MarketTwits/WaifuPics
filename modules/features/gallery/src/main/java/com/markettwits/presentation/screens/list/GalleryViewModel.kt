package com.markettwits.presentation.screens.list

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.presentation.navigation.GalleryRouter
import com.markettwits.presentation.screens.ImageFavoriteUi
import com.markettwits.presentation.screens.list.communication.DetailCommunication
import com.markettwits.presentation.screens.list.communication.GalleryCommunication
import com.markettwits.presentation.screens.list.communication.SelectedImageCommunication
import com.markettwits.presentation.screens.list.communication.SelectedModeCommunication
import kotlinx.coroutines.flow.StateFlow

interface GalleryViewModel {
    fun toDetail(state: ImageFavoriteUi)
    fun favoriteImages()
    fun deleteImage(item: String, id: Long)
    fun selectedPhotoState(): List<ImageFavoriteUi>
    fun selectedState(): StateFlow<Boolean>
    fun selection(index: Int)
    fun shareImages()
    fun delete()
    fun changeSelectedState()

    class Base(
        private val gallery: GalleryCommunication,
        private val repository: GalleryRepository,
        private val async: AsyncViewModel.Abstract<List<ImageFavoriteUi>>,
        private val current: DetailCommunication,
        private val selectedModeCommunication: SelectedModeCommunication,
        private val navigation: GalleryRouter,
        private val selectedImageCommunication: SelectedImageCommunication,
        private val imageIntentAction: ImageIntentAction.ShareImage
    ) : ViewModel(), StateCommunication.State<List<ImageFavoriteUi>>, GalleryViewModel {

        init {
            favoriteImages()
        }

        override fun selectedPhotoState(): List<ImageFavoriteUi> =
            selectedImageCommunication.fetch()

        override fun selectedState(): StateFlow<Boolean> = selectedModeCommunication.state()

        override fun toDetail(state: ImageFavoriteUi) {
            current.map(state)
            navigation.toDetailImageScreen()
        }

        override fun favoriteImages() {
            async.handleAsyncSingle {
                repository.observe().collect {
                    gallery.map(it)
                }
            }
        }

        override fun deleteImage(item: String, id: Long) {
            async.handleAsync({
                repository.delete(item, id)
            }) {
                favoriteImages()
            }
        }

        override fun selection(index: Int) {
            async.handleAsyncSingle {
                val selectedPhotoState = selectedImageCommunication.fetch()
                val item = gallery.state().value[index]
                val selectedPhoto = selectedPhotoState.find { it.id == item.id }
                if (selectedPhoto != null)
                    selectedImageCommunication.remove(selectedPhoto)
                else
                    selectedImageCommunication.add(item)
                if (selectedPhotoState.isEmpty()) changeSelectedState()
            }
        }

        override fun shareImages() {
            val imageUrl = selectedImageCommunication.fetch().map { it.imageUrl }
            async.handleAsyncSingle {
                imageIntentAction.shareImage(imageUrl)
            }
        }

        override fun delete() {
            val selectedPhotoState = selectedImageCommunication.fetch()
            val id = selectedPhotoState.map { it.id }
            val url = selectedPhotoState.map { it.imageUrl }
            async.handleAsyncSingle {
                repository.delete(id, url)
            }
            changeSelectedState()
        }

        override fun changeSelectedState() {
            val currentState = selectedModeCommunication.state().value
            if (currentState) selectedImageCommunication.clear()
            selectedModeCommunication.map(!currentState)
        }

        override fun state() = gallery.state()
    }
}



