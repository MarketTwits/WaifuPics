package com.markettwits.waifupics.gallery.items.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.snapshots.Snapshot
import com.markettwits.waifupics.settings.WaifuPicsAppSettings
import com.markettwits.waifupics.gallery.items.model.GalleryLayout
import com.markettwits.waifupics.gallery.items.model.toggleGalleryGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.markettwits.async.AsyncViewModel
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.waifupics.communication.StateCommunication
import com.markettwits.waifupics.gallery.common.GalleryRepository
import com.markettwits.waifupics.gallery.items.components.communication.DetailCommunication
import com.markettwits.waifupics.gallery.items.components.communication.GalleryCommunication
import com.markettwits.waifupics.gallery.items.components.communication.SelectedImageCommunication
import com.markettwits.waifupics.gallery.items.components.communication.SelectedModeCommunication
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUiState
import kotlinx.coroutines.flow.StateFlow

interface GalleryViewModel : StateCommunication.State<ImageFavoriteUiState> {

    fun toDetail(state: ImageFavoriteUi)

    fun favoriteImages()

    fun deleteImage(item: String, id: Int)

    fun selectedPhotoState(): List<ImageFavoriteUi>

    fun selectedState(): StateFlow<Boolean>

    fun selection(id: Int)

    fun selectGroup(ids: Set<Int>)

    fun replaceSelection(ids: Set<Int>, keepMode: Boolean = false)

    fun finishSelectionGesture()

    fun layout(): StateFlow<GalleryLayout>

    fun setLayout(layout: GalleryLayout)

    fun shareImages()

    fun delete()

    fun changeSelectedState()

    class Base(
        private val galleryCommunication: GalleryCommunication,
        private val repository: GalleryRepository,
        private val async: AsyncViewModel.Abstract<List<ImageFavoriteUi>>,
        private val detailCommunication: DetailCommunication,
        private val selectedModeCommunication: SelectedModeCommunication,
        private val selectedImageCommunication: SelectedImageCommunication,
        private val imageIntentAction: ImageIntentAction.ShareImage,
        private val settings: WaifuPicsAppSettings
    ) : ViewModel(), StateCommunication.State<ImageFavoriteUiState>, GalleryViewModel {

        private val layoutState = MutableStateFlow(GalleryLayout())

        init {
            favoriteImages()
            viewModelScope.launch {
                settings.observeSettings().collect {
                    layoutState.value = GalleryLayout(it.galleryColumns.coerceIn(0, 6), it.galleryGroupByMonth,
                        it.galleryKeepAspectRatio)
                }
            }
        }

        override fun layout(): StateFlow<GalleryLayout> = layoutState

        override fun setLayout(layout: GalleryLayout) {
            viewModelScope.launch {
                val current = settings.settings()
                settings.updateSettings(current.copy(galleryColumns = layout.columns.coerceIn(0, 6),
                    galleryGroupByMonth = layout.byMonth, galleryKeepAspectRatio = layout.keepAspectRatio))
            }
        }

        override fun selectedPhotoState(): List<ImageFavoriteUi> = selectedImageCommunication.fetch()

        override fun selectedState(): StateFlow<Boolean> = selectedModeCommunication.state()

        override fun toDetail(state: ImageFavoriteUi) {
            detailCommunication.map(state)
        }

        override fun favoriteImages() {
            async.handleAsyncSingle {
                repository.observe().collect {
                    galleryCommunication.map(it)
                }
            }
        }

        override fun deleteImage(item: String, id: Int) {
            async.handleAsync({
                repository.delete(id)
            }) {
                favoriteImages()
            }
        }

        override fun selection(id: Int) {
            val original = selectedImageCommunication.fetch().map { it.id }.toSet()
            replaceSelection(if (id in original) original - id else original + id)
        }

        override fun selectGroup(ids: Set<Int>) {
            replaceSelection(toggleGalleryGroup(selectedImageCommunication.fetch().map { it.id }.toSet(), ids))
        }

        override fun replaceSelection(ids: Set<Int>, keepMode: Boolean) {
            val available = galleryCommunication.state().value.items.filterIsInstance<ImageFavoriteUi.Base>()
                .associateBy { it.id }
            val validIds = ids.intersect(available.keys)
            Snapshot.withMutableSnapshot {
                selectedImageCommunication.fetch().toList().filter { it.id !in validIds }
                    .forEach { selectedImageCommunication.remove(it) }
                val current = selectedImageCommunication.fetch().map { it.id }.toSet()
                (validIds - current).forEach { selectedImageCommunication.add(available.getValue(it)) }
            }
            selectedModeCommunication.map(keepMode || validIds.isNotEmpty())
        }

        override fun finishSelectionGesture() {
            selectedModeCommunication.map(selectedImageCommunication.fetch().isNotEmpty())
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

        override fun state() = galleryCommunication.state()
    }
}


