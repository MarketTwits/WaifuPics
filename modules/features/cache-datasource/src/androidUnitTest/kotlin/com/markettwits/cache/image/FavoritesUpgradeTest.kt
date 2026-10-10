package com.markettwits.cache.image

import com.markettwits.waifupics.cache.kstore.listStoreOfWrapper
import kotlinx.coroutines.test.runTest
import java.io.File
import java.nio.file.Files
import kotlin.test.*

/** Host Android tests use the application's real file codec and the unchanged file name. */
class FavoritesUpgradeTest {
    private val legacyJson = """[
      {"id":1,"netUrl":"https://images.example/1.jpg","width":640,"height":480,"created":1699999999999,"protected":false},
      {"id":2,"netUrl":"https://images.example/2.jpg","width":480,"height":640,"created":1700000000000,"protected":true}
    ]"""
    private val metadata = FavoriteImageMetadata("Artist", "safe", "https://example.com/art/3",
        listOf("cat", "blue"), listOf(listOf(30, 60, 90)))
    private val added = ImageFavoriteCache(3, "https://images.example/3.jpg", 500, 700, 1700000000001, false, metadata)

    private suspend fun withOldFile(block: suspend (File, ImagesCacheDataSource) -> Unit) {
        val directory = Files.createTempDirectory("waifupics-upgrade-").toFile()
        val file = File(directory, "nekosApiImages")
        file.writeText(legacyJson)
        try { block(file, data(directory)) } finally { directory.deleteRecursively() }
    }
    private fun data(directory: File) = ImagesCacheDataSource(listStoreOfWrapper<ImageFavoriteCache>(directory.path, "nekosApiImages"))

    @Test fun upgradeReadsOldFileWithoutRewritingOrChangingDates() = runTest {
        withOldFile { file, data ->
            val bytes = file.readBytes()
            val images = data.getAll()
            assertEquals(listOf(1, 2), images.map { it.id })
            assertEquals(listOf(1699999999999, 1700000000000), images.map { it.created })
            assertTrue(images.all { it.metadata == null })
            assertContentEquals(bytes, file.readBytes())
        }
    }

    @Test fun addingMetadataImageThenRestartingKeepsAllLegacyRecords() = runTest {
        withOldFile { file, data ->
            val before = data.getAll()
            data.write(added)
            val reopened = data(requireNotNull(file.parentFile)).getAll()
            assertEquals(before + added, reopened)
            assertEquals(metadata, reopened.last().metadata)
            assertFalse(File(file.path + ".temp").exists())
        }
    }

    @Test fun removingAndUndoingMixedRecordsThenRestartingPreservesMetadataAndOrder() = runTest {
        withOldFile { file, data ->
            data.write(added)
            val before = data.getAll()
            val removal = data.removeForUndo(setOf(1, 3))
            assertEquals(listOf(2), data.getAll().map { it.id })
            data.restoreRemoved(listOf(removal))
            assertEquals(before, data(requireNotNull(file.parentFile)).getAll())
        }
    }

    @Test fun deletingAllAndUndoingKeepsProtectedLegacyRecordAndNewMetadata() = runTest {
        withOldFile { file, data ->
            data.write(added)
            val before = data.getAll()
            val removal = data.removeForUndo(setOf(1, 2, 3))
            assertTrue(data.getAll().isEmpty())
            data.restoreRemoved(listOf(removal))
            assertEquals(before, data(requireNotNull(file.parentFile)).getAll())
            assertTrue(data.getAll()[1].protected)
        }
    }

    @Test fun invalidExistingFileIsNeverReplacedWithAnEmptyGallery() = runTest {
        withOldFile { file, data ->
            file.writeText("[{\"id\":1}]")
            val bytes = file.readBytes()
            assertFailsWith<kotlinx.serialization.SerializationException> { data.write(added) }
            assertContentEquals(bytes, file.readBytes())
        }
    }
}
