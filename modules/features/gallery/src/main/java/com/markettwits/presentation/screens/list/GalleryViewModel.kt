package com.markettwits.presentation.screens.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.navigation.GalleryRouter
import com.markettwits.presentation.screens.ImageFavoriteUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface GalleryViewModel {
    fun toDetail(state: ImageFavoriteUi)
    fun favoriteImages()
    fun deleteImage(item: String, id: Long)
    fun selected(): StateFlow<Boolean>
    fun selection(index: Int)
    fun changeSelectedState()
    val selectedPhotoState: SnapshotStateList<ImageFavoriteUi>

    class Base(
        private val gallery: GalleryCommunication,
        private val repository: GalleryRepository,
        private val async: AsyncViewModel.Abstract<List<ImageFavoriteUi>>,
        private val current: DetailCommunication,
        private val selectedImageCommunication: SelectedImageCommunication,
        private val navigation: GalleryRouter,
    ) : ViewModel(), StateCommunication.State<List<ImageFavoriteUi>>, GalleryViewModel {
        override val selectedPhotoState = mutableStateListOf<ImageFavoriteUi>()

        init {
            favoriteImages()
        }

        override fun toDetail(state: ImageFavoriteUi) {
            current.map(state)
            navigation.toDetailImageScreen()
        }

        override fun favoriteImages() {
            viewModelScope.launch {
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

        override fun selected(): StateFlow<Boolean> {
            return selectedImageCommunication.state()
        }

        override fun selection(index: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                val item = gallery.state().value[index]
                val selectedPhoto = selectedPhotoState.find { it.id() == item.id() }
                if (selectedPhoto != null)
                    selectedPhotoState.remove(selectedPhoto)
                else
                    selectedPhotoState.add(item)
                if (selectedPhotoState.isEmpty()) changeSelectedState()
            }
        }

        override fun changeSelectedState() {
            val currentState = selectedImageCommunication.state().value
            if (currentState) selectedPhotoState.clear()

            selectedImageCommunication.map(!currentState)
        }

        override fun state() = gallery.state()
    }
}

interface GalleryCommunication : StateCommunication.Mutable<List<ImageFavoriteUi>> {
    class Base : StateCommunication.Abstract<List<ImageFavoriteUi>>(listOf()), GalleryCommunication
}

interface DetailCommunication : StateCommunication.Mutable<ImageFavoriteUi> {
    class Base : StateCommunication.Abstract<ImageFavoriteUi>(ImageFavoriteUi.Initial),
        DetailCommunication
}

interface SelectedImageCommunication : StateCommunication.Mutable<Boolean> {
    class Base : StateCommunication.Abstract<Boolean>(false),
        SelectedImageCommunication
}

