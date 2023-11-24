package com.markettwits.random_image.presentation.random_image_screen

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.random_image.data.RandomImageRepository
import com.markettwits.random_image.presentation.features.filter.ProtectedMapper
import com.markettwits.random_image.presentation.features.filter.presentation.FilterCommunication
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface ImageViewModel {

    fun loadedImageState(): StateFlow<LoadedImage>
    fun currentImage(image: Drawable, networkUrl: String, id: Int)
    fun shareImage()
    fun fetchRandomImage()
    fun addToFavorite()
    fun reported()
    fun imageLoading()

    class Base(
        private val filterResult: FilterCommunication,
        private val protectedMapper: ProtectedMapper,
        private val async: AsyncViewModel.Abstract<RandomImageUiState>,
        private val randomImageCommunication: RandomImageCommunication,
        private val loadedImageCommunication: LoadedImageCommunication,
        private val repository: RandomImageRepository,
        private val shareImageAction: ImageIntentAction.ShareImage
    ) : ViewModel(), StateCommunication.State<RandomImageUiState>, ImageViewModel {
        init {
            randomImageCommunication.map(RandomImageUiState.Progress)
            fetchRandomImage()
        }

        override fun loadedImageState() = loadedImageCommunication.state()


        override fun currentImage(image: Drawable, networkUrl: String, id: Int) {
            loadedImageCommunication.map(
                LoadedImage.Loaded(
                    id,
                    image,
                    networkUrl,
                    protectedMapper.map(filterResult.fetch() ?: listOf("safe"))
                )
            )
        }

        override fun shareImage() {
            viewModelScope.launch {
                shareImageAction.shareImage((loadedImageCommunication.state().value as LoadedImage.Loaded).image)
            }
        }

        override fun fetchRandomImage() {
            randomImageCommunication.map(RandomImageUiState.Progress)
            loadedImageCommunication.map(LoadedImage.Loading)
            async.handleAsync({
                repository.fetchRandomImage(filterResult.fetch() ?: listOf("safe"))
            }) {
                if (it is RandomImageUiState.Error){
                    loadedImageCommunication.map(LoadedImage.LoadedError)
                }
                randomImageCommunication.map(it)
            }
        }

        override fun addToFavorite() {
            val state = loadedImageCommunication.state().value as LoadedImage.Loaded
            async.handleAsync({
                repository.addToFavorite(
                    state.image,
                    state.networkUrl,
                    state.protected
                )
            }) {}
        }

        override fun reported() {
            val state = loadedImageCommunication.state().value as LoadedImage.Loaded
            async.handleAsync({
                repository.reportImage(state.id)
            }) {
                //TODO add request
            }
        }

        override fun imageLoading() {
            loadedImageCommunication.map(LoadedImage.Loading)
        }

        override fun state() = randomImageCommunication.state()
    }
}

interface RandomImageCommunication : StateCommunication.Mutable<RandomImageUiState> {
    class Base : StateCommunication.Abstract<RandomImageUiState>(RandomImageUiState.Initial),
        RandomImageCommunication
}

sealed class LoadedImage {
    data class Loaded(
        val id: Int,
        val image: Drawable,
        val networkUrl: String,
        val protected: Boolean,
    ) : LoadedImage()

    data object Loading : LoadedImage()
    data object LoadedError : LoadedImage()
}

interface LoadedImageCommunication : StateCommunication.Mutable<LoadedImage> {
    class Base :
        StateCommunication.Abstract<LoadedImage>(
            LoadedImage.Loading,
        ),
        LoadedImageCommunication
}

