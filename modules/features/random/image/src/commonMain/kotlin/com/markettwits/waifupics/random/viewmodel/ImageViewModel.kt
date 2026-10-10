package com.markettwits.waifupics.random.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markettwits.cache.image.ImageCacheRepository
import com.markettwits.cache.image.FavoriteImageMetadata
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.waifupics.communication.StateCommunication
import com.markettwits.waifupics.filter.viewmodel.FilterCommunication
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.data.RandomImageRepository
import com.markettwits.waifupics.random.model.RandomImageState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface ImageViewModel : StateCommunication.State<RandomImageState> {
    fun images(): StateFlow<List<RandomImageState>>
    fun currentIndex(): StateFlow<Int>
    fun loadedImageState(): StateFlow<ImageState>
    fun favorites(): StateFlow<Set<Int>>
    fun requestedPage(): StateFlow<PagerNavigation?>
    fun isFetching(): StateFlow<Boolean>
    fun loadingError(): StateFlow<String?>
    fun retryLoading()
    fun onNavigationCompleted(request: PagerNavigation, settledPage: Int = request.page)
    fun onPageChanged(index: Int)
    fun onClickShareImage()
    fun onClickAddToFavorite()
    fun fetchRandomImage()
    fun obtainImageState(id: Int, imageUrl: String, state: ImageState)

    class Base(
        private val filterResult: FilterCommunication,
        private val randomImageCommunication: RandomImageCommunication,
        private val loadedImageCommunication: LoadedImageCommunication,
        private val repository: RandomImageRepository,
        private val shareImageAction: ImageIntentAction.ShareImage,
        private val cache: ImageCacheRepository
    ) : ViewModel(), ImageViewModel {
        private val images = MutableStateFlow<List<RandomImageState>>(emptyList())
        private val currentIndex = MutableStateFlow(0)
        private val loadedById = mutableMapOf<Int, ImageState>()
        private val favorites = MutableStateFlow<Set<Int>>(emptySet())
        private val requestedPage = MutableStateFlow<PagerNavigation?>(null)
        private val isFetching = MutableStateFlow(false)
        private val loadingError = MutableStateFlow<String?>(null)
        private var navigationSequence = 0L
        private var batchJob: Job? = null
        private var generation = 0
        private var filters: List<String> = emptyList()

        init {
            viewModelScope.launch {
                try {
                    cache.observe().collect { list ->
                        favorites.value = list.map { it.id }.toSet()
                    }
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    showError(e)
                }
            }
            viewModelScope.launch {
                filterResult.state().collect { selected ->
                    filters = selected.toList()
                    generation++
                    batchJob?.cancel()
                    navigateTo(0)
                    images.value = emptyList()
                    loadedById.clear()
                    currentIndex.value = 0
                    loadedImageCommunication.map(ImageState.Loading)
                    randomImageCommunication.map(RandomImageState.Progress)
                    requestBatch(advance = false)
                }
            }
        }

        override fun images() = images.asStateFlow()
        override fun currentIndex() = currentIndex.asStateFlow()
        override fun loadedImageState() = loadedImageCommunication.state()
        override fun favorites() = favorites.asStateFlow()
        override fun requestedPage() = requestedPage.asStateFlow()
        override fun isFetching() = isFetching.asStateFlow()
        override fun loadingError() = loadingError.asStateFlow()

        override fun retryLoading() {
            if (batchJob?.isActive == true || requestedPage.value != null) return
            if (images.value.isEmpty()) randomImageCommunication.map(RandomImageState.Progress)
            requestBatch(advance = false)
        }
        override fun state() = randomImageCommunication.state()

        private fun currentImage() =
            images.value.getOrNull(currentIndex.value) as? RandomImageState.Success

        private fun currentLoadedImage(): ImageState.Success? =
            if (requestedPage.value != null) null
            else currentImage()?.let { loadedById[it.id] as? ImageState.Success }

        private fun navigateTo(page: Int) {
            requestedPage.value = PagerNavigation(++navigationSequence, page)
        }

        override fun onNavigationCompleted(request: PagerNavigation, settledPage: Int) {
            if (requestedPage.value != request || settledPage !in images.value.indices) return
            currentIndex.value = settledPage
            publishCurrentImage()
            requestedPage.value = null
        }

        override fun onClickShareImage() {
            val image = currentLoadedImage() ?: return
            viewModelScope.launch {
                try {
                    shareImageAction.shareImage(image.networkUrl)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    showError(e)
                }
            }
        }

        override fun onClickAddToFavorite() {
            val image = currentLoadedImage() ?: return
            val info = currentImage()?.takeIf { it.id == image.id && it.imageUrl == image.networkUrl } ?: return
            val metadata = FavoriteImageMetadata(info.imageData.artistName, info.imageData.ageRating,
                info.imageData.sourceUrl, info.imageData.tags, info.colorPalette)
            viewModelScope.launch {
                try {
                    repository.toggleFavorite(
                        image.id, image.width, image.height, image.networkUrl, image.protected, metadata
                    )
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    showError(e)
                }
            }
        }

        override fun fetchRandomImage() {
            if (requestedPage.value != null) return
            val next = currentIndex.value + 1
            if (next in images.value.indices) {
                navigateTo(next)
            } else if (batchJob?.isActive != true) {
                if (images.value.isEmpty()) randomImageCommunication.map(RandomImageState.Progress)
                requestBatch(advance = true)
            }
        }

        private fun requestBatch(advance: Boolean) {
            val requestGeneration = generation
            val requestFilters = filters
            val requestedIndex = currentIndex.value
            isFetching.value = true
            loadingError.value = null
            batchJob = viewModelScope.launch {
                try {
                    val batch = repository.fetchRandomImages(requestFilters, 10)
                    // A cancelled source may still finish; old filters must not re-enter the feed.
                    if (requestGeneration != generation) return@launch
                    val existingIds = images.value.filterIsInstance<RandomImageState.Success>()
                        .map { it.id }.toSet()
                    val additions = batch.distinctBy { it.id }.filter { it.id !in existingIds }
                    if (additions.isEmpty()) {
                        if (images.value.isEmpty()) requestedPage.value = null
                        val message = "Нет новых изображений. Попробуйте ещё раз."
                        loadingError.value = message
                        randomImageCommunication.map(RandomImageState.Error(message))
                        return@launch
                    }
                    val wasEmpty = images.value.isEmpty()
                    images.value = images.value + additions
                    if (wasEmpty) {
                        currentIndex.value = 0
                        if (requestedPage.value == null) navigateTo(0)
                    }
                    else if (advance && currentIndex.value == requestedIndex) {
                        navigateTo((requestedIndex + 1).coerceAtMost(images.value.lastIndex))
                    }
                    publishCurrentImage()
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    if (requestGeneration == generation) {
                        if (images.value.isEmpty()) requestedPage.value = null
                        loadingError.value = e.message ?: "Не удалось выполнить действие"
                        showError(e)
                    }
                } finally {
                    if (requestGeneration == generation) isFetching.value = false
                }
            }
        }

        override fun onPageChanged(index: Int) {
            if (index !in images.value.indices || requestedPage.value != null) return
            currentIndex.value = index
            publishCurrentImage()
            if (batchJob?.isActive != true && images.value.size - index <= 3) {
                requestBatch(advance = false)
            }
        }

        private fun publishCurrentImage() {
            val image = currentImage() ?: return
            randomImageCommunication.map(image)
            loadedImageCommunication.map(loadedById[image.id] ?: ImageState.Loading)
        }

        override fun obtainImageState(id: Int, imageUrl: String, state: ImageState) {
            val image = images.value.filterIsInstance<RandomImageState.Success>()
                .firstOrNull { it.id == id && it.imageUrl == imageUrl } ?: return
            val normalized = if (state is ImageState.Success) {
                if (state.id != id || state.networkUrl != imageUrl) return
                state.copy(protected = image.imageData.ageRating in setOf("borderline", "explicit"))
            } else state
            loadedById[id] = normalized
            if (currentImage()?.id == id) loadedImageCommunication.map(normalized)
        }

        private fun showError(error: Exception) {
            randomImageCommunication.map(RandomImageState.Error(error.message ?: "Не удалось выполнить действие"))
        }
    }
}

data class PagerNavigation(val sequence: Long, val page: Int)

interface RandomImageCommunication : StateCommunication.Mutable<RandomImageState> {
    class Base : StateCommunication.Abstract<RandomImageState>(RandomImageState.Initial),
        RandomImageCommunication
}

interface LoadedImageCommunication : StateCommunication.Mutable<ImageState> {
    class Base : StateCommunication.Abstract<ImageState>(ImageState.Loading),
        LoadedImageCommunication
}
