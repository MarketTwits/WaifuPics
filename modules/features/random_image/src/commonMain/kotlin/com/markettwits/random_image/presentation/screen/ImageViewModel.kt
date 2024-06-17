package com.markettwits.random_image.presentation.screen

import androidx.lifecycle.ViewModel
import com.markettwits.core.communication.StateCommunication
import com.markettwits.core.wrappers.AsyncViewModel
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.random_image.data.RandomImageRepository
import com.markettwits.random_image.presentation.components.filter.ProtectedMapper
import com.markettwits.random_image.presentation.components.filter.presentation.FilterCommunication
import com.markettwits.random_image.presentation.components.image.ImageState
import kotlinx.coroutines.flow.StateFlow

interface ImageViewModel {

    fun loadedImageState(): StateFlow<ImageState>

    fun currentImage(networkUrl: String, id: Int)

    fun onClickShareImage()

    fun onClickReported()

    fun onClickAddToFavorite()

    fun fetchRandomImage()

    fun obtainImageState(state: ImageState)

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


        override fun currentImage(networkUrl: String, id: Int) {
            loadedImageCommunication.map(
                ImageState.Success(
                    id,
                    networkUrl,
                    protectedMapper.map(filterResult.state().value ?: listOf("safe"))
                )
            )
        }

        override fun onClickShareImage() {
           async.handleAsyncSingle {
               shareImageAction.shareImage((loadedImageCommunication.state().value as ImageState.Success).networkUrl)
           }
        }

        override fun fetchRandomImage() {
            randomImageCommunication.map(RandomImageUiState.Progress)
            loadedImageCommunication.map(ImageState.Loading)
            async.handleAsync({
                repository.fetchRandomImage(filterResult.state().value ?: listOf("safe"))
            }) {
                if (it is RandomImageUiState.Error) {
                    loadedImageCommunication.map(ImageState.Error())
                }
                randomImageCommunication.map(it)
            }
        }

        override fun onClickAddToFavorite() {
            val success = loadedImageCommunication.state().value as ImageState.Success
            async.handleAsync({
                repository.addToFavorite(
                    success.networkUrl,
                    success.protected
                )
            }) {}
        }

        override fun onClickReported() {
            val success = loadedImageCommunication.state().value as ImageState.Success
            async.handleAsync({
                repository.reportImage(success.id)
            }) {
                //TODO add request
            }
        }

        override fun obtainImageState(state: ImageState) {
            loadedImageCommunication.map(state)
            if (state is ImageState.Error) randomImageCommunication.map(
                RandomImageUiState.Error(
                    state.message
                )
            )
        }

        override fun state() = randomImageCommunication.state()
    }
}

interface RandomImageCommunication : StateCommunication.Mutable<RandomImageUiState> {
    class Base : StateCommunication.Abstract<RandomImageUiState>(RandomImageUiState.Initial),
        RandomImageCommunication
}

interface LoadedImageCommunication : StateCommunication.Mutable<ImageState> {
    class Base :
        StateCommunication.Abstract<ImageState>(
            ImageState.Loading,
        ),
        LoadedImageCommunication
}


