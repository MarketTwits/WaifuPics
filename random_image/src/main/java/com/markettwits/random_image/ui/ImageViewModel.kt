package com.markettwits.random_image.ui

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.filter.presentation.FilterCommunication
import com.markettwits.random_image.data.RandomImageRepository
import com.markettwits.random_image.ui.bottom_pannel.share.ShareImage

interface ImageViewModel {
    fun shareImage(imageUrl : String)
    fun fetchRandomImage()
    fun addToFavorite(url : String, protected : Boolean)
    class Base(
        private val filterResult: FilterCommunication,
        private val async: AsyncViewModel.Abstract<RandomImageUiState>,
        private val randomImageCommunication: RandomImageCommunication,
        private val repository: RandomImageRepository,
        private val shareImage: ShareImage
    ) : ViewModel(), StateCommunication.State<RandomImageUiState>, ImageViewModel {
        init {
            randomImageCommunication.map(RandomImageUiState.Progress)
            fetchRandomImage()
        }

        override fun shareImage(imageUrl : String) {
            shareImage.shareImageUrl(imageUrl)
        }

        override fun fetchRandomImage() {
            randomImageCommunication.map(RandomImageUiState.Progress)
            async.handleAsync({
                repository.fetchRandomImage(filterResult.fetch() ?: listOf("safe"))
            }) {
                randomImageCommunication.map(it)
            }
        }

        override fun addToFavorite(url : String, protected : Boolean) {
            async.handleAsync({
                repository.addToFavorite(url, protected)
            }){}
        }

        override fun state() = randomImageCommunication.state()
    }
}




interface RandomImageCommunication : StateCommunication.Mutable<RandomImageUiState> {
    class Base : StateCommunication.Abstract<RandomImageUiState>(RandomImageUiState.Initial),
        RandomImageCommunication
}


