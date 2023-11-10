package com.markettwits.presentation.detail

import androidx.lifecycle.ViewModel
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.data.GalleryRepository
import com.markettwits.presentation.list.DetailCommunication
import kotlinx.coroutines.flow.StateFlow

interface GalleryScreenViewModel {
    fun setImageAs()
    fun delete()
    fun infoAboutImage()
    fun saveToGallery()
    fun state(): StateFlow<ImageFavoriteUi>
    class Base(
        private val item: DetailCommunication,
        private val async: AsyncViewModel<Unit>,
        private val repository: GalleryRepository,
    ) : ViewModel(), GalleryScreenViewModel {
        override fun state() = item.state()
        override fun setImageAs() {

        }

        override fun delete() {
            async.handleAsync({
                repository.delete(state().value.imageUrl(), state().value.id())
            }) {}
        }

        override fun infoAboutImage() {

        }

        override fun saveToGallery() {
            async.handleAsync({
                repository.saveToGallery(state().value.imageUrl())
            }) {}
        }
    }
}
