package com.markettwits.cloud_datasource.presentation.random_image.viewmodel

import androidx.lifecycle.ViewModel
import com.markettwits.async.communication.StateCommunication
import com.markettwits.async.wrappers.AsyncViewModel
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.waifupics.random_image.common.RandomImageRepository
import com.markettwits.waifupics.random_image.filter.domain.ProtectedMapper
import com.markettwits.waifupics.random_image.filter.viewmodel.FilterCommunication
import com.markettwits.waifupics.random_image.image.components.image_state.ImageState
import com.markettwits.waifupics.random_image.image.model.RandomImageState
import kotlinx.coroutines.flow.StateFlow

interface ImageViewModel : StateCommunication.State<RandomImageState> {

    fun loadedImageState(): StateFlow<ImageState>

    fun currentImage(networkUrl: String, id: Int, width: Int, height: Int)

    fun onClickShareImage()

    fun onClickReported()

    fun onClickAddToFavorite()

    fun fetchRandomImage()

    fun obtainImageState(state: ImageState)

    class Base(
        private val filterResult: FilterCommunication,
        private val protectedMapper: ProtectedMapper,
        private val async: AsyncViewModel.Abstract<RandomImageState>,
        private val randomImageCommunication: RandomImageCommunication,
        private val loadedImageCommunication: LoadedImageCommunication,
        private val repository: RandomImageRepository,
        private val shareImageAction: ImageIntentAction.ShareImage
    ) : ViewModel(), StateCommunication.State<RandomImageState>, ImageViewModel {

        init {
            randomImageCommunication.map(RandomImageState.Progress)
            fetchRandomImage()
        }

        override fun loadedImageState() = loadedImageCommunication.state()

        override fun currentImage(networkUrl: String, id: Int, width: Int, height: Int) {
            loadedImageCommunication.map(
                ImageState.Success(
                    id,
                    networkUrl,
                    width,
                    height,
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
            randomImageCommunication.map(RandomImageState.Progress)
            loadedImageCommunication.map(ImageState.Loading)
            async.handleAsync({
                repository.fetchRandomImage(filterResult.state().value ?: listOf("safe"))
            }) {
                if (it is RandomImageState.Error) {
                    loadedImageCommunication.map(ImageState.Error())
                }
                randomImageCommunication.map(it)
            }
        }

        override fun onClickAddToFavorite() {
            val success = loadedImageCommunication.state().value as ImageState.Success
            async.handleAsync({
                repository.addToFavorite(
                    success.id,
                    success.width,
                    success.height,
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
                RandomImageState.Error(state.message)
            )
        }

        override fun state() = randomImageCommunication.state()
    }
}

interface RandomImageCommunication : StateCommunication.Mutable<RandomImageState> {
    class Base : StateCommunication.Abstract<RandomImageState>(RandomImageState.Initial),
        RandomImageCommunication
}

interface LoadedImageCommunication : StateCommunication.Mutable<ImageState> {
    class Base : StateCommunication.Abstract<ImageState>(ImageState.Loading),
        LoadedImageCommunication
}


