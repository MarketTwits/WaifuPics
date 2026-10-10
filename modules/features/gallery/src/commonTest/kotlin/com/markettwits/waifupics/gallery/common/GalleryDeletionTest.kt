package com.markettwits.waifupics.gallery.common

import com.markettwits.cache.image.FavoriteImageRemoval
import com.markettwits.cache.image.ImageCacheRepository
import com.markettwits.cache.image.ImageFavoriteCache
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class GalleryDeletionTest {
    @Test fun rapidDeletionsShareUndoAndEarlierVisibleActionStillWorks() = runTest {
        val cache = FakeCache()
        val deletion = GalleryDeletion(cache)
        assertTrue(deletion.delete(setOf(1)))
        val first = deletion.notice.value!!
        assertTrue(deletion.delete(setOf(2, 3)))
        assertEquals(3, deletion.notice.value!!.count)
        assertTrue(deletion.undo(first.token))
        assertEquals(listOf(1, 2, 3), cache.restored.flatMap { it.removed }.map { it.id })
        assertNull(deletion.notice.value)
        assertFalse(deletion.undo(first.token))
    }

    @Test fun oldDismissCannotDiscardNewBatchAndExpiredUndoCannotRestoreIt() = runTest {
        val deletion = GalleryDeletion(FakeCache())
        deletion.delete(setOf(1))
        val old = deletion.notice.value!!.token
        deletion.delete(setOf(2))
        deletion.dismiss(old)
        assertEquals(2, deletion.notice.value!!.count)
        deletion.dismiss(deletion.notice.value!!.token)
        deletion.delete(setOf(3))
        assertFalse(deletion.undo(old))
        assertEquals(1, deletion.notice.value!!.count)
    }

    @Test fun duplicateDeletionDoesNotResetMessageOrCount() = runTest {
        val deletion = GalleryDeletion(FakeCache())
        deletion.delete(setOf(1))
        val notice = deletion.notice.value
        assertFalse(deletion.delete(setOf(1)))
        assertEquals(notice, deletion.notice.value)
        assertFalse(deletion.delete(emptySet()))
    }

    @Test fun restoreFailureKeepsSnapshotsForRetry() = runTest {
        val cache = FakeCache()
        val deletion = GalleryDeletion(cache)
        deletion.delete(setOf(2))
        cache.failRestore = true
        assertFalse(deletion.undo(deletion.notice.value!!.token))
        assertEquals(GalleryDeletionError.Restore, deletion.notice.value!!.error)
        assertEquals(1, deletion.notice.value!!.count)
        cache.failRestore = false
        assertTrue(deletion.undo(deletion.notice.value!!.token))
        assertEquals(2, cache.restored.single().removed.single().id)
    }

    @Test fun deleteFailureDoesNotAnnounceSuccessAndPreservesEarlierUndo() = runTest {
        val cache = FakeCache()
        val deletion = GalleryDeletion(cache)
        cache.failDelete = true
        assertFalse(deletion.delete(setOf(1)))
        assertEquals(GalleryDeletionError.Delete, deletion.notice.value!!.error)
        assertEquals(0, deletion.notice.value!!.count)
        cache.failDelete = false
        deletion.delete(setOf(1))
        cache.failDelete = true
        assertFalse(deletion.delete(setOf(2)))
        assertEquals(1, deletion.notice.value!!.count)
        assertTrue(deletion.undo(deletion.notice.value!!.token))
    }

    @Test fun leavingInitiatingScreenDuringWriteDoesNotLoseUndoSnapshot() = runTest {
        val cache = FakeCache()
        val gate = CompletableDeferred<Unit>()
        cache.beforeRemove = { gate.await() }
        val deletion = GalleryDeletion(cache)
        val job = launch { deletion.delete(setOf(1)) }
        runCurrent()
        job.cancel()
        gate.complete(Unit)
        job.join()
        assertEquals(1, deletion.notice.value!!.count)
        assertTrue(deletion.undo(deletion.notice.value!!.token))
    }

    @Test fun viewerUndoReportsOriginalImageAndAtomicRestoreUsesOneCall() = runTest {
        val cache = FakeCache()
        val deletion = GalleryDeletion(cache)
        var focus: Int? = null
        val subscriber = launch { focus = deletion.restoredFocus.first() }
        runCurrent()
        deletion.delete(setOf(1), viewerFocus = 1)
        deletion.delete(setOf(2), viewerFocus = 2)
        deletion.undo(deletion.notice.value!!.token)
        subscriber.join()
        assertEquals(2, focus)
        assertEquals(1, cache.restoreCalls)
    }

    @Test fun concurrentDeletesAreSerializedIntoOneUndoBatch() = runTest {
        val deletion = GalleryDeletion(FakeCache())
        val jobs = (1..5).map { id -> launch { deletion.delete(setOf(id)) } }
        jobs.forEach { it.join() }
        assertEquals(5, deletion.notice.value!!.count)
        assertTrue(deletion.undo(deletion.notice.value!!.token))
    }

    private class FakeCache : ImageCacheRepository {
        private var images = (1..5).map { ImageFavoriteCache(it, "image-$it", 400, 600, it.toLong(), false) }
        var restored = emptyList<FavoriteImageRemoval>()
        var restoreCalls = 0
        var failDelete = false
        var failRestore = false
        var beforeRemove: suspend () -> Unit = {}
        override suspend fun removeForUndo(ids: Set<Int>): FavoriteImageRemoval {
            beforeRemove()
            if (failDelete) error("write failed")
            val removal = FavoriteImageRemoval(images, images.filter { it.id in ids })
            images = images.filterNot { it.id in ids }
            return removal
        }
        override suspend fun restoreRemoved(removals: List<FavoriteImageRemoval>) {
            if (failRestore) error("write failed")
            restored = removals
            restoreCalls++
        }
        override fun observe() = flowOf(images)
        override suspend fun fetch() = images
        override suspend fun addToFavorite(id: Int, width: Int, height: Int, url: String, isProtected: Boolean) = Unit
        override suspend fun addOrDelete(id: Int, width: Int, height: Int, url: String, isProtected: Boolean) = Unit
        override suspend fun deleteList(id: List<Int>, url: List<String>) = Unit
        override suspend fun delete(id: Int) = Unit
        override suspend fun delete(imageUrl: String) = Unit
    }
}
