package com.markettwits.cache.image

import io.github.xxfast.kstore.Codec
import io.github.xxfast.kstore.KStore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class FavoriteImageRemovalTest {
    private fun image(id: Int) = ImageFavoriteCache(id, "image-$id", 300 + id, 500 + id, 1000L + id, id % 2 == 0)
    private val original = (1..5).map(::image)

    @Test fun singleAndBatchRestoreExactRecordsAndOrder() {
        for (ids in listOf(setOf(3), setOf(1, 3, 5), setOf(1, 2, 3, 4, 5))) {
            val removal = FavoriteImageRemoval(original, original.filter { it.id in ids })
            assertEquals(original, restoreFavoriteImages(original.filterNot { it.id in ids }, listOf(removal)))
        }
    }

    @Test fun newerSavesAndUnrelatedDeletionsSurviveUndo() {
        val removal = FavoriteImageRemoval(original, listOf(image(2), image(4)))
        val current = listOf(image(1), image(5), image(6)) // 3 was deleted separately; 6 was added.
        assertEquals(listOf(1, 2, 4, 5, 6), restoreFavoriteImages(current, listOf(removal)).map { it.id })
    }

    @Test fun restoringAllKeepsNewSavesAtTheEnd() {
        val removal = FavoriteImageRemoval(original, original)
        assertEquals(original + image(6), restoreFavoriteImages(listOf(image(6)), listOf(removal)))
    }

    @Test fun alreadyResavedImagesAreNotDuplicatedOrOverwritten() {
        val newer = image(3).copy(created = 9999, protected = true)
        val removal = FavoriteImageRemoval(original, listOf(image(3)))
        val current = original.filterNot { it.id == 3 } + newer
        assertEquals(current, restoreFavoriteImages(current, listOf(removal)))
        val sameUrl = newer.copy(id = 33)
        val byUrl = original.filterNot { it.id == 3 } + sameUrl
        assertEquals(byUrl, restoreFavoriteImages(byUrl, listOf(removal)))
    }

    @Test fun sequentialBatchesRestoreTogetherWithoutChangingEqualTimestampOrder() {
        val original = this.original.map { it.copy(created = 1000) }
        val afterFirst = original.filterNot { it.id in setOf(1, 4) }
        val first = FavoriteImageRemoval(original, original.filter { it.id in setOf(1, 4) })
        val second = FavoriteImageRemoval(afterFirst, afterFirst.filter { it.id in setOf(2, 5) })
        assertEquals(original, restoreFavoriteImages(afterFirst.filter { it.id == 3 }, listOf(first, second)))
    }

    @Test fun largeBatchAndRepeatedUndoKeepOrderAndUniqueIds() {
        val original = (1..10000).map(::image)
        val removal = FavoriteImageRemoval(original, original.filter { it.id % 2 == 0 })
        val restored = restoreFavoriteImages(original.filter { it.id % 2 == 1 }, listOf(removal))
        assertEquals(original, restored)
        assertEquals(original, restoreFavoriteImages(restored, listOf(removal)))
    }

    @Test fun actualStoreDeletesBatchInOneWriteAndRestoresMetadata() = runTest {
        val codec = MemoryCodec(original)
        val data = ImagesCacheDataSource(KStore(default = emptyList(), codec = codec))
        val removal = data.removeForUndo(setOf(1, 3, 5, 99))
        assertEquals(1, codec.writes)
        assertEquals(listOf(image(2), image(4)), data.getAll())
        data.write(image(6))
        data.restoreRemoved(listOf(removal))
        assertEquals(original + image(6), data.getAll())
        assertEquals(3, codec.writes)
    }

    @Test fun concurrentRemovalsAndSaveNeverLoseOtherRecords() = runTest {
        val data = ImagesCacheDataSource(KStore(default = emptyList(), codec = MemoryCodec(original)))
        val first = async { data.removeForUndo(setOf(1, 2)) }
        val second = async { data.removeForUndo(setOf(3, 4)) }
        val addition = async { data.write(image(6)) }
        addition.await()
        // Calls can acquire the store lock in either order; restore in actual deletion order.
        val removals = awaitAll(first, second).sortedByDescending { it.before.size }
        assertEquals(setOf(5, 6), data.getAll().map { it.id }.toSet())
        data.restoreRemoved(removals)
        assertEquals(original + image(6), data.getAll())
    }

    @Test fun repeatedDeleteAndEmptyStoreAreSafe() = runTest {
        val data = ImagesCacheDataSource(KStore(default = emptyList(), codec = MemoryCodec(null)))
        data.delete(99)
        data.delete("missing")
        data.write(image(1))
        assertEquals(listOf(image(1)), data.getAll())
        assertEquals(listOf(image(1)), data.removeForUndo(setOf(1)).removed)
        assertTrue(data.removeForUndo(setOf(1)).removed.isEmpty())
    }

    private class MemoryCodec(var value: List<ImageFavoriteCache>?) : Codec<List<ImageFavoriteCache>> {
        var writes = 0
        override suspend fun encode(value: List<ImageFavoriteCache>?) { this.value = value; writes++ }
        override suspend fun decode() = value
    }
}
