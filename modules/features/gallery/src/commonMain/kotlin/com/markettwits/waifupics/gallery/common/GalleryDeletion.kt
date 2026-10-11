package com.markettwits.waifupics.gallery.common

import com.markettwits.cache.image.FavoriteImageRemoval
import com.markettwits.cache.image.ImageCacheRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

enum class GalleryDeletionError { Delete, Restore }
data class GalleryDeletionNotice(val token: Long, val count: Int, val error: GalleryDeletionError? = null)

/** A single undo window shared by the grid and viewer; rapid deletions form one batch. */
class GalleryDeletion(private val cache: ImageCacheRepository) {
    private val mutex = Mutex()
    private val removals = mutableListOf<FavoriteImageRemoval>()
    private var version = 0L
    private var batchStartToken: Long? = null
    private var focusId: Int? = null
    private val mutableNotice = MutableStateFlow<GalleryDeletionNotice?>(null)
    val notice = mutableNotice.asStateFlow()
    private val mutableRestoredFocus = MutableSharedFlow<Int>(extraBufferCapacity = 1)
    val restoredFocus = mutableRestoredFocus.asSharedFlow()

    suspend fun delete(ids: Set<Int>, viewerFocus: Int? = null): Boolean = mutex.withLock {
        if (ids.isEmpty()) return@withLock false
        // Do not lose an undo snapshot if the initiating screen leaves during the store write.
        withContext(NonCancellable) {
            try {
                val removal = cache.removeForUndo(ids)
                if (removal.removed.isEmpty()) return@withContext false
                removals.add(removal)
                focusId = viewerFocus
                publish()
                true
            } catch (_: Exception) {
                publish(GalleryDeletionError.Delete)
                false
            }
        }
    }

    suspend fun undo(token: Long): Boolean = mutex.withLock {
        val current = mutableNotice.value ?: return@withLock false
        val start = batchStartToken ?: return@withLock false
        if (token !in start..current.token || removals.isEmpty()) return@withLock false
        withContext(NonCancellable) {
            try {
                cache.restoreRemoved(removals.toList())
                focusId?.let { mutableRestoredFocus.tryEmit(it) }
                clear()
                true
            } catch (_: Exception) {
                // Retain the snapshot so the same action can be retried.
                publish(GalleryDeletionError.Restore)
                false
            }
        }
    }

    suspend fun dismiss(token: Long) = mutex.withLock {
        if (mutableNotice.value?.token == token) clear()
    }

    private fun publish(error: GalleryDeletionError? = null) {
        val count = removals.sumOf { it.removed.size }
        val token = ++version
        if (count > 0 && batchStartToken == null) batchStartToken = token
        mutableNotice.value = GalleryDeletionNotice(token, count, error)
    }

    private fun clear() {
        removals.clear()
        focusId = null
        batchStartToken = null
        mutableNotice.value = null
    }
}
