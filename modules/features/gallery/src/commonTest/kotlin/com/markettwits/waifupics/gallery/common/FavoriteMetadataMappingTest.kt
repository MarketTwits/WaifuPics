package com.markettwits.waifupics.gallery.common

import com.markettwits.cache.image.FavoriteImageMetadata
import com.markettwits.cache.image.ImageFavoriteCache
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import kotlin.test.*

class FavoriteMetadataMappingTest {
    @Test fun savedMetadataSurvivesMappingForNormalAndProtectedImages() {
        val metadata = FavoriteImageMetadata("Artist", "safe", "https://example.com/art", listOf("art"), listOf(listOf(1, 2, 3)))
        for (protected in listOf(false, true)) {
            val image = FavoriteImageCacheToUiMapper.Base().map(ImageFavoriteCache(1, "image-1", 300, 400, 1700000000000, protected, metadata))
            assertEquals(metadata, image.metadata)
            assertEquals(1700000000000, image.savedAt)
            assertEquals(protected, image is ImageFavoriteUi.Protected)
        }
    }
    @Test fun oldSavedImageRemainsAvailableWithNoInventedMetadata() {
        val image = FavoriteImageCacheToUiMapper.Base().map(ImageFavoriteCache(1, "image-1", 300, 400, 1700000000000, false))
        assertIs<ImageFavoriteUi.Base>(image)
        assertNull(image.metadata)
        assertEquals("image-1", image.imageUrl)
    }
}
