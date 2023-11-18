package com.markettwits.random_image.ui

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core_ui.image.ShareImage
import com.markettwits.filter.ProtectedMapper
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.random_image.data.RandomImageRepository
import kotlinx.coroutines.flow.StateFlow

interface ImageViewModel {
    fun loadedImageState(): StateFlow<LoadedImage>
    fun currentImage(image: Drawable, networkUrl: String, id: Int)
    fun shareImage(imageUrl: String)
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
        private val shareImage: ShareImage,
    ) : ViewModel(), StateCommunication.State<RandomImageUiState>, ImageViewModel {
        init {
            randomImageCommunication.map(RandomImageUiState.Progress)
            fetchRandomImage()
        }

        override fun loadedImageState() =
            loadedImageCommunication.state()


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

        @Deprecated("Actual variant with drawable, use shareImage()")
        override fun shareImage(imageUrl: String) {
            shareImage.shareImageUrl(imageUrl)
        }

        override fun shareImage() {
            shareImage.shareImageDrawable((loadedImageCommunication.state().value as LoadedImage.Loaded).image)
        }

        override fun fetchRandomImage() {
            randomImageCommunication.map(RandomImageUiState.Progress)
            loadedImageCommunication.map(LoadedImage.Loading)
            async.handleAsync({
                repository.fetchRandomImage(filterResult.fetch() ?: listOf("safe"))
            }) {
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

//data class LoadedImage(val id : Int, val image: Drawable, val networkUrl: String, val protected: Boolean)
sealed class LoadedImage {
    data class Loaded(
        val id: Int,
        val image: Drawable,
        val networkUrl: String,
        val protected: Boolean,
    ) : LoadedImage()

    data object Loading : LoadedImage()
}

interface LoadedImageCommunication : StateCommunication.Mutable<LoadedImage> {
    class Base :
        StateCommunication.Abstract<LoadedImage>(
            LoadedImage.Loading,
        ),
        LoadedImageCommunication
}


