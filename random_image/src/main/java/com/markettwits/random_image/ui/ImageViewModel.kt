package com.markettwits.random_image.ui

import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.core_ui.image.ShareImage
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.random_image.data.RandomImageRepository

interface ImageViewModel {
    fun currentImage(image: Drawable, networkUrl: String)
    fun shareImage(imageUrl: String)
    fun shareImage()
    fun fetchRandomImage()
    fun addToFavorite()
    class Base(
        private val filterResult: FilterCommunication,
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

        override fun currentImage(image: Drawable, networkUrl: String) {
            loadedImageCommunication.map(FavoriteImage(image, networkUrl))
        }
        @Deprecated("Actual variant with drawable, use shareImage()")
        override fun shareImage(imageUrl: String) {
            shareImage.shareImageUrl(imageUrl)
        }

        override fun shareImage() {
            shareImage.shareImageDrawable(loadedImageCommunication.state().value.image)
        }

        override fun fetchRandomImage() {
            randomImageCommunication.map(RandomImageUiState.Progress)
            async.handleAsync({
                repository.fetchRandomImage(filterResult.fetch() ?: listOf("safe"))
            }) {
                randomImageCommunication.map(it)
            }
        }

        override fun addToFavorite() {
            async.handleAsync({
                repository.addToFavorite(
                    loadedImageCommunication.state().value.image,
                    loadedImageCommunication.state().value.networkUrl,
                    false
                )
            }) {}
        }

        override fun state() = randomImageCommunication.state()
    }
}

interface RandomImageCommunication : StateCommunication.Mutable<RandomImageUiState> {
    class Base : StateCommunication.Abstract<RandomImageUiState>(RandomImageUiState.Initial),
        RandomImageCommunication
}

data class FavoriteImage(val image: Drawable, val networkUrl: String)
interface LoadedImageCommunication : StateCommunication.Mutable<FavoriteImage> {
    class Base :
        StateCommunication.Abstract<FavoriteImage>(FavoriteImage(ShapeDrawable(OvalShape()), "")),
        LoadedImageCommunication
}


