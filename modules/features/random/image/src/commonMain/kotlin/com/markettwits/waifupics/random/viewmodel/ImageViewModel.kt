package com.markettwits.waifupics.random.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.async.AsyncViewModel
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.waifupics.communication.StateCommunication
import com.markettwits.waifupics.random.cache.ImageCacheManager
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.data.RandomImageRepository
import com.markettwits.waifupics.random.model.RandomImageState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface ImageViewModel : StateCommunication.State<RandomImageState> {

    fun loadedImageState(): StateFlow<ImageState>

    fun currentImage(networkUrl: String, id: Int, width: Int, height: Int)

    fun onClickShareImage()

    fun onClickAddToFavorite()

    fun fetchRandomImage()

    fun obtainImageState(state: ImageState)

    class Base(
        private val filterResult: com.markettwits.waifupics.filter.viewmodel.FilterCommunication,
        private val protectedMapper: com.markettwits.waifupics.filter.domain.ProtectedMapper,
        private val async: AsyncViewModel.Abstract<RandomImageState>,
        private val randomImageCommunication: RandomImageCommunication,
        private val loadedImageCommunication: LoadedImageCommunication,
        private val repository: RandomImageRepository,
        private val shareImageAction: ImageIntentAction.ShareImage,
        private val cacheManager: ImageCacheManager = ImageCacheManager.Base()
    ) : ViewModel(), StateCommunication.State<RandomImageState>, ImageViewModel {

        private var isLoadingBatch = false

        init {
            randomImageCommunication.map(RandomImageState.Progress)
            loadInitialBatch()
        }

        override fun loadedImageState() = loadedImageCommunication.state()

        override fun currentImage(networkUrl: String, id: Int, width: Int, height: Int) {
            loadedImageCommunication.map(
                ImageState.Success(
                    id,
                    networkUrl,
                    width,
                    height,
                    protectedMapper.map(filterResult.state().value)
                )
            )
        }

        override fun onClickShareImage() {
            async.handleAsyncSingle {
                shareImageAction.shareImage((loadedImageCommunication.state().value as ImageState.Success).networkUrl)
            }
        }

        override fun fetchRandomImage() {
            // Попытка получить изображение из кеша
            val cachedImage = cacheManager.getNextImage()

            if (cachedImage != null && cachedImage is RandomImageState.Success) {
                // Показываем закешированное изображение без задержки
                randomImageCommunication.map(cachedImage)

                // Проверяем, нужна ли подгрузка новых изображений
                if (cacheManager.shouldLoadMore() && !isLoadingBatch) {
                    loadMoreImages()
                }
            } else {
                // Если кеш пуст, загружаем изображение напрямую
                randomImageCommunication.map(RandomImageState.Progress)
                loadedImageCommunication.map(ImageState.Loading)
                async.handleAsync({
                    repository.fetchRandomImage(filterResult.state().value)
                }) {
                    if (it is RandomImageState.Error) {
                        loadedImageCommunication.map(ImageState.Error())
                    }
                    randomImageCommunication.map(it)
                }
            }
        }

        private fun loadInitialBatch() {
            viewModelScope.launch {
                isLoadingBatch = true
                try {
                    val images = repository.fetchRandomImages(filterResult.state().value, 10)
                    cacheManager.addImages(images)

                    // Показываем первое изображение
                    val firstImage = cacheManager.getNextImage()
                    if (firstImage != null) {
                        randomImageCommunication.map(firstImage)
                    }
                } catch (e: Exception) {
                    randomImageCommunication.map(RandomImageState.Error(e.message ?: "Unknown error"))
                } finally {
                    isLoadingBatch = false
                }
            }
        }

        private fun loadMoreImages() {
            viewModelScope.launch {
                isLoadingBatch = true
                try {
                    val images = repository.fetchRandomImages(filterResult.state().value, 10)
                    cacheManager.addImages(images)
                } catch (e: Exception) {
                    // Ошибку при фоновой загрузке можно игнорировать или логировать
                } finally {
                    isLoadingBatch = false
                }
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


