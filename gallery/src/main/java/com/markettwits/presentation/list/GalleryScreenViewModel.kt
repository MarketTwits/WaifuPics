package com.markettwits.presentation.list

import androidx.lifecycle.ViewModel
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.detail.ImageFavoriteUi
import kotlinx.coroutines.flow.StateFlow

interface GalleryScreenViewModel {
    fun delete()
    fun saveToGallery()
    fun state(): StateFlow<ImageFavoriteUi>
    class Base(
        private val item: DetailCommunication,
        private val async: AsyncViewModel<Unit>,
        private val repository: GalleryRepository
    ) : ViewModel(), GalleryScreenViewModel {
        override fun state() = item.state()
        override fun delete() {
            async.handleAsync({
                repository.delete(state().value.imageUrl(), state().value.id())
            }) {}
        }

        override fun saveToGallery() {
            async.handleAsync({
                repository.saveToGallery(state().value.imageUrl())
            }) {}
        }
    }
}

interface GalleryScreenFuture {
    fun delete(imageId: Long)
    fun swipeLeft()
    fun swipeRight()
}