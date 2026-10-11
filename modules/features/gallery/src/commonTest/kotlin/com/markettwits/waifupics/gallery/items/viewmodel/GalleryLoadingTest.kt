package com.markettwits.waifupics.gallery.items.viewmodel

import com.markettwits.waifupics.gallery.common.GalleryRepository
import com.markettwits.waifupics.gallery.items.components.communication.GalleryCommunication
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUiState
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryLoadingTest {
    private val image = ImageFavoriteUi.Base(1, "image-1", 400, 600, "10.10.2026", false)

    @Test fun delayedReadNeverAnnouncesEmptyBeforeReturningImages() = runTest {
        val communication = GalleryCommunication.Base()
        val ready = CompletableDeferred<Unit>()
        val reader = launch {
            observeGalleryImages(repository { flow {
                ready.await()
                emit(ImageFavoriteUiState.Base(listOf(image)))
            } }, communication)
        }
        runCurrent()
        assertIs<ImageFavoriteUiState.Loading>(communication.state().value)
        ready.complete(Unit)
        reader.join()
        assertEquals(listOf(image), communication.state().value.items)
        assertIs<ImageFavoriteUiState.Base>(communication.state().value)
    }

    @Test fun genuinelyEmptyGalleryAppearsOnlyAfterReadCompletes() = runTest {
        val communication = GalleryCommunication.Base()
        val ready = CompletableDeferred<Unit>()
        val reader = launch {
            observeGalleryImages(repository { flow {
                ready.await()
                emit(ImageFavoriteUiState.Empty)
            } }, communication)
        }
        runCurrent()
        assertIs<ImageFavoriteUiState.Loading>(communication.state().value)
        ready.complete(Unit)
        reader.join()
        assertIs<ImageFavoriteUiState.Empty>(communication.state().value)
    }

    @Test fun readFailureShowsErrorAndRetryWaitsForActualSnapshot() = runTest {
        val communication = GalleryCommunication.Base()
        observeGalleryImages(repository { flow { error("Unreadable file") } }, communication)
        assertIs<ImageFavoriteUiState.Error>(communication.state().value)
        val ready = CompletableDeferred<Unit>()
        val retry = launch {
            observeGalleryImages(repository { flow {
                ready.await()
                emit(ImageFavoriteUiState.Base(listOf(image)))
            } }, communication)
        }
        runCurrent()
        assertIs<ImageFavoriteUiState.Loading>(communication.state().value)
        ready.complete(Unit)
        retry.join()
        assertEquals(listOf(image), communication.state().value.items)
    }

    @Test fun failureOpeningTheRepositoryAlsoLeavesLoadingState() = runTest {
        val communication = GalleryCommunication.Base()
        observeGalleryImages(repository { error("Cannot open store") }, communication)
        assertIs<ImageFavoriteUiState.Error>(communication.state().value)
    }

    @Test fun laterReadFailureDoesNotEraseAlreadyDisplayedImages() = runTest {
        val communication = GalleryCommunication.Base()
        observeGalleryImages(repository { flow {
            emit(ImageFavoriteUiState.Base(listOf(image)))
            error("Read failed after initial snapshot")
        } }, communication)
        assertEquals(listOf(image), assertIs<ImageFavoriteUiState.Error>(communication.state().value).items)
        observeGalleryImages(repository { flowOf(ImageFavoriteUiState.Empty) }, communication)
        assertIs<ImageFavoriteUiState.Empty>(communication.state().value)
    }

    @Test fun cancellationIsNotReportedAsReadFailure() = runTest {
        val communication = GalleryCommunication.Base()
        val reader = launch {
            observeGalleryImages(repository { flow { awaitCancellation() } }, communication)
        }
        runCurrent()
        reader.cancelAndJoin()
        assertIs<ImageFavoriteUiState.Loading>(communication.state().value)
    }

    private fun repository(snapshots: suspend () -> Flow<ImageFavoriteUiState>) = object : GalleryRepository {
        override suspend fun observe() = snapshots()
        override suspend fun delete(id: Int) = Unit
        override suspend fun delete(id: List<Int>, url: List<String>) = Unit
        override suspend fun saveToGallery(url: String) = Unit
    }
}
