package com.markettwits.waifupics.random

import androidx.lifecycle.ViewModelStore
import com.markettwits.cache.image.ImageCacheRepository
import com.markettwits.cache.image.ImageFavoriteCache
import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.waifupics.filter.viewmodel.FilterCommunication
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.data.RandomImageRepository
import com.markettwits.waifupics.random.model.ImageSourceUi
import com.markettwits.waifupics.random.model.RandomImageState
import com.markettwits.waifupics.random.viewmodel.ImageViewModel
import com.markettwits.waifupics.random.viewmodel.LoadedImageCommunication
import com.markettwits.waifupics.random.viewmodel.RandomImageCommunication
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.test.assertNull
import kotlin.test.assertFalse

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class ImageViewModelTest {
    private val store = ViewModelStore()
    private lateinit var filters: FilterCommunication
    private lateinit var repository: FakeRepository
    private lateinit var share: FakeShare
    private lateinit var model: ImageViewModel.Base

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        filters = FilterCommunication.Base()
        repository = FakeRepository()
        share = FakeShare()
        model = ImageViewModel.Base(
            filters, RandomImageCommunication.Base(), LoadedImageCommunication.Base(),
            repository, share, FakeCache()
        )
        store.put("feed", model)
    }

    @AfterTest
    fun tearDown() {
        store.clear()
        Dispatchers.resetMain()
    }

    @Test
    fun neighboringPagesCannotOverwriteActiveStateOrActions() = runTest {
        settle()
        model.obtainImageState(1, image(1).imageUrl, loaded(1))
        model.obtainImageState(2, image(2).imageUrl, loaded(2))
        model.obtainImageState(2, image(2).imageUrl, ImageState.Error("neighbor failed"))
        model.onClickShareImage()
        model.onClickAddToFavorite()
        settle()
        assertEquals(1, assertIs<ImageState.Success>(model.loadedImageState().value).id)
        assertEquals(listOf(image(1).imageUrl), share.urls)
        assertEquals(listOf(1), repository.favorites.map { it.first })
        assertIs<RandomImageState.Success>(model.state().value)
    }

    @Test
    fun unloadedActivePageNeverUsesLoadedNeighborAsFallback() = runTest {
        settle()
        model.obtainImageState(1, image(1).imageUrl, loaded(1))
        model.onPageChanged(1)
        model.onClickShareImage()
        model.onClickAddToFavorite()
        settle()
        assertIs<ImageState.Loading>(model.loadedImageState().value)
        assertTrue(share.urls.isEmpty())
        assertTrue(repository.favorites.isEmpty())
        model.obtainImageState(2, image(2).imageUrl, loaded(2))
        model.onClickShareImage()
        settle()
        assertEquals(listOf(image(2).imageUrl), share.urls)
    }

    @Test
    fun actionCapturesImageAtClickEvenIfPageChangesBeforeCoroutineRuns() = runTest {
        settle()
        model.obtainImageState(1, image(1).imageUrl, loaded(1))
        model.obtainImageState(2, image(2).imageUrl, loaded(2))
        model.onClickShareImage()
        model.onClickAddToFavorite()
        model.onPageChanged(1)
        settle()
        assertEquals(listOf(image(1).imageUrl), share.urls)
        assertEquals(listOf(1), repository.favorites.map { it.first })
    }

    @Test
    fun savingFavoriteCapturesMetadataForClickedImageBeforePageChanges() = runTest {
        repository.fetch = { listOf(
            image(1).copy(colorPalette = listOf(listOf(1, 2, 3)),
                imageData = ImageSourceUi("https://example.com/art/1", "safe", "Artist 1", listOf("tag-1"))),
            image(2).copy(colorPalette = listOf(listOf(4, 5, 6)),
                imageData = ImageSourceUi("https://example.com/art/2", "suggestive", "Artist 2", listOf("tag-2")))
        ) }
        settle()
        model.obtainImageState(1, image(1).imageUrl, loaded(1))
        model.obtainImageState(2, image(2).imageUrl, loaded(2))
        model.onClickAddToFavorite()
        model.onPageChanged(1)
        settle()
        assertEquals(com.markettwits.cache.image.FavoriteImageMetadata("Artist 1", "safe", "https://example.com/art/1",
            listOf("tag-1"), listOf(listOf(1, 2, 3))), repository.favoriteMetadata.single())
    }

    @Test
    fun filterChangeRejectsLateResponseAndClearsLoadedState() = runTest {
        val oldResponse = CompletableDeferred<List<RandomImageState.Success>>()
        repository.fetch = { selected ->
            if (selected == listOf("safe")) withContext(NonCancellable) { oldResponse.await() }
            else listOf(image(99))
        }
        runCurrent()
        filters.map(listOf("suggestive"))
        runCurrent()
        assertEquals(listOf(99), model.images().value.map { (it as RandomImageState.Success).id })
        oldResponse.complete(listOf(image(1)))
        settle()
        model.obtainImageState(1, image(1).imageUrl, loaded(1))
        assertEquals(listOf(99), model.images().value.map { (it as RandomImageState.Success).id })
        assertIs<ImageState.Loading>(model.loadedImageState().value)
        assertEquals(listOf(listOf("safe"), listOf("suggestive")), repository.requests)
    }

    @Test
    fun changingFiltersResetsPageAndFavoriteUsesActualImageRating() = runTest {
        settle()
        model.onPageChanged(1)
        repository.fetch = { listOf(image(99, "explicit")) }
        filters.map(listOf("safe", "explicit"))
        settle()
        assertEquals(0, model.currentIndex().value)
        assertIs<ImageState.Loading>(model.loadedImageState().value)
        model.obtainImageState(99, image(99).imageUrl, loaded(99))
        model.onClickAddToFavorite()
        settle()
        assertEquals(listOf(99 to true), repository.favorites)
    }

    @Test
    fun initialNetworkFailureCanBeRetried() = runTest {
        repository.fetch = { throw IllegalStateException("network unavailable") }
        settle()
        assertEquals("network unavailable", assertIs<RandomImageState.Error>(model.state().value).message)
        repository.fetch = { listOf(image(1)) }
        model.fetchRandomImage()
        settle()
        assertEquals(1, assertIs<RandomImageState.Success>(model.state().value).id)
    }

    @Test
    fun failedPaginationKeepsFeedAndDeduplicatesRetryWithoutMovingPage() = runTest {
        settle()
        repository.fetch = { throw IllegalStateException("timeout") }
        model.onPageChanged(2)
        settle()
        assertEquals(5, model.images().value.size)
        assertEquals("timeout", assertIs<RandomImageState.Error>(model.state().value).message)
        repository.fetch = { listOf(image(5), image(6), image(6)) }
        model.onPageChanged(2)
        settle()
        assertEquals((1..6).toList(), model.images().value.map { (it as RandomImageState.Success).id })
        assertEquals(2, model.currentIndex().value)
    }

    @Test
    fun repeatedNextClicksAtEndDoNotLaunchConcurrentBatches() = runTest {
        settle()
        val response = CompletableDeferred<List<RandomImageState.Success>>()
        repository.fetch = { response.await() }
        model.onPageChanged(4)
        runCurrent()
        repeat(5) { model.fetchRandomImage() }
        runCurrent()
        assertEquals(2, repository.requests.size)
        response.complete(listOf(image(6)))
        settle()
        assertEquals(6, model.images().value.size)
        assertEquals(4, model.currentIndex().value)
    }

    @Test
    fun manualSwipeSelectionNeverIssuesAnAnimationCommand() = runTest {
        settle()
        model.onPageChanged(1)
        assertEquals(1, model.currentIndex().value)
        assertNull(model.requestedPage().value)
        model.onPageChanged(0)
        assertEquals(0, model.currentIndex().value)
        assertNull(model.requestedPage().value)
    }

    @Test
    fun nextWaitsForPagerAndIgnoresIntermediatePageReports() = runTest {
        settle()
        model.obtainImageState(1, image(1).imageUrl, loaded(1))
        model.fetchRandomImage()
        val request = model.requestedPage().value!!
        assertEquals(1, request.page)
        assertEquals(0, model.currentIndex().value)
        model.onPageChanged(0)
        model.onClickShareImage()
        runCurrent()
        assertTrue(share.urls.isEmpty())
        assertEquals(request, model.requestedPage().value)
        model.onNavigationCompleted(request)
        assertEquals(1, model.currentIndex().value)
        assertNull(model.requestedPage().value)
    }

    @Test
    fun staleNavigationCompletionCannotOverrideFilterReset() = runTest {
        settle()
        model.fetchRandomImage()
        val oldRequest = model.requestedPage().value!!
        repository.fetch = { listOf(image(99), image(100)) }
        filters.map(listOf("suggestive"))
        runCurrent()
        val reset = model.requestedPage().value!!
        model.onNavigationCompleted(oldRequest)
        assertEquals(reset, model.requestedPage().value)
        assertEquals(0, model.currentIndex().value)
        model.onNavigationCompleted(reset)
        assertEquals(99, assertIs<RandomImageState.Success>(model.state().value).id)
    }

    @Test
    fun interruptedNextCanSettleOnTheOriginalPageWithoutLeavingActionsLocked() = runTest {
        settle()
        model.obtainImageState(1, image(1).imageUrl, loaded(1))
        model.fetchRandomImage()
        val request = model.requestedPage().value!!
        model.onNavigationCompleted(request, settledPage = 0)
        assertEquals(0, model.currentIndex().value)
        assertNull(model.requestedPage().value)
        model.onClickShareImage()
        runCurrent()
        assertEquals(listOf(image(1).imageUrl), share.urls)
    }

    @Test
    fun loadingIndicatorCoversTheRequestAndClearsAfterFailureAndRetry() = runTest {
        val response = CompletableDeferred<List<RandomImageState.Success>>()
        repository.fetch = { response.await() }
        runCurrent()
        assertTrue(model.isFetching().value)
        response.completeExceptionally(IllegalStateException("offline"))
        settle()
        assertFalse(model.isFetching().value)
        repository.fetch = { listOf(image(1)) }
        model.retryLoading()
        assertTrue(model.isFetching().value)
        settle()
        assertFalse(model.isFetching().value)
        assertEquals(1, assertIs<RandomImageState.Success>(model.state().value).id)
    }

    @Test
    fun oldCancelledRequestCannotHideNewFilterLoadingIndicator() = runTest {
        val oldResponse = CompletableDeferred<List<RandomImageState.Success>>()
        val newResponse = CompletableDeferred<List<RandomImageState.Success>>()
        repository.fetch = { selected ->
            if (selected == listOf("safe")) withContext(NonCancellable) { oldResponse.await() }
            else newResponse.await()
        }
        runCurrent()
        filters.map(listOf("suggestive"))
        runCurrent()
        oldResponse.complete(listOf(image(1)))
        runCurrent()
        assertTrue(model.isFetching().value)
        newResponse.complete(listOf(image(99)))
        settle()
        assertFalse(model.isFetching().value)
        assertEquals(99, assertIs<RandomImageState.Success>(model.state().value).id)
    }

    @Test
    fun explicitPaginationRetryDoesNotAdvanceOrLaunchDuplicateRequests() = runTest {
        settle()
        repository.fetch = { throw IllegalStateException("offline") }
        model.onPageChanged(4)
        settle()
        val response = CompletableDeferred<List<RandomImageState.Success>>()
        repository.fetch = { response.await() }
        repeat(3) { model.retryLoading() }
        runCurrent()
        assertEquals(3, repository.requests.size)
        assertTrue(model.isFetching().value)
        response.complete(listOf(image(6)))
        settle()
        assertEquals(4, model.currentIndex().value)
        assertNull(model.requestedPage().value)
        assertFalse(model.isFetching().value)
    }

    private fun TestScope.settle() {
        advanceUntilIdle()
        model.requestedPage().value?.let { model.onNavigationCompleted(it) }
        advanceUntilIdle()
    }

    private fun image(id: Int, rating: String = "safe") = RandomImageState.Success(
        id, "https://images.example/$id.jpg", emptyList(), ImageSourceUi("", rating)
    )
    private fun loaded(id: Int) = ImageState.Success(id, image(id).imageUrl, 400, 600, false)

    private inner class FakeRepository : RandomImageRepository {
        var fetch: suspend (List<String>) -> List<RandomImageState.Success> = { (1..5).map { image(it) } }
        val requests = mutableListOf<List<String>>()
        val favorites = mutableListOf<Pair<Int, Boolean>>()
        val favoriteMetadata = mutableListOf<com.markettwits.cache.image.FavoriteImageMetadata?>()
        override suspend fun fetchRandomImages(filters: List<String>, limit: Int): List<RandomImageState.Success> {
            requests.add(filters)
            return fetch(filters)
        }
        override suspend fun fetchRandomImage(filters: List<String>): RandomImageState = fetch(filters).first()
        override suspend fun toggleFavorite(id: Int, width: Int, height: Int, url: String, isProtected: Boolean, metadata: com.markettwits.cache.image.FavoriteImageMetadata?) {
            favorites.add(id to isProtected)
            favoriteMetadata.add(metadata)
        }
    }
    private class FakeShare : ImageIntentAction.ShareImage {
        val urls = mutableListOf<String>()
        override suspend fun shareImage(imagePath: String) { urls.add(imagePath) }
        override suspend fun shareImage(imagePath: List<String>) { urls.addAll(imagePath) }
    }
}

internal class FakeCache : ImageCacheRepository {
    override suspend fun removeForUndo(ids: Set<Int>) = com.markettwits.cache.image.FavoriteImageRemoval(emptyList(), emptyList())
    override suspend fun restoreRemoved(removals: List<com.markettwits.cache.image.FavoriteImageRemoval>) = Unit
    override fun observe() = flowOf(emptyList<ImageFavoriteCache>())
    override suspend fun fetch() = emptyList<ImageFavoriteCache>()
    override suspend fun addToFavorite(id: Int, width: Int, height: Int, url: String, isProtected: Boolean, metadata: com.markettwits.cache.image.FavoriteImageMetadata?) = Unit
    override suspend fun addOrDelete(id: Int, width: Int, height: Int, url: String, isProtected: Boolean, metadata: com.markettwits.cache.image.FavoriteImageMetadata?) = Unit
    override suspend fun deleteList(id: List<Int>, url: List<String>) = Unit
    override suspend fun delete(id: Int) = Unit
    override suspend fun delete(imageUrl: String) = Unit
}
