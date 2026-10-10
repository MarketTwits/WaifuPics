package com.markettwits.cache.image

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.*

class FavoriteMetadataCompatibilityTest {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    @Test fun oldRecordsWithoutMetadataKeepEveryOriginalField() {
        val old = LegacyFavorite(42, "https://images.example/42.jpg", 640, 480, 1700000000000, true)
        val decoded = json.decodeFromString<List<ImageFavoriteCache>>(json.encodeToString(listOf(old))).single()
        assertEquals(ImageFavoriteCache(old.id, old.netUrl, old.width, old.height, old.created, old.protected), decoded)
        assertNull(decoded.metadata)
        assertEquals(old, json.decodeFromString<List<LegacyFavorite>>(json.encodeToString(listOf(decoded))).single())
    }

    @Test fun mixedOldAndNewRecordsRoundTripIncludingNullAndPartialMetadata() {
        val values = listOf(ImageFavoriteCache(1, "image-1", 100, 200, 1000, false),
            ImageFavoriteCache(2, "image-2", 200, 100, 1001, true, FavoriteImageMetadata(tags = listOf("art"))),
            ImageFavoriteCache(3, "image-3", 400, 300, 1002, false,
                FavoriteImageMetadata("Artist", "safe", "https://example.com/art", listOf("cat", "blue"), listOf(listOf(1, 2, 3)))))
        assertEquals(values, json.decodeFromString<List<ImageFavoriteCache>>(json.encodeToString(values)))
        assertEquals(values.map { it.id }, json.decodeFromString<List<LegacyFavorite>>(json.encodeToString(values)).map { it.id })
    }

    @Test fun emptyMetadataAndUnknownFutureFieldsUseDefaults() {
        val value = """[{"id":1,"netUrl":"image-1","width":100,"height":200,"created":1000,"protected":false,
            "futureField":true,"metadata":{"futureMetadata":"unknown"}}]"""
        val image = json.decodeFromString<List<ImageFavoriteCache>>(value).single()
        assertEquals(FavoriteImageMetadata(), image.metadata)
    }

    @Test fun normalizationHandlesIncompletePalettesAndTagsWithoutChangingIdentity() {
        val metadata = FavoriteImageMetadata("  Artist  ", "safe", " https://example.com/art ",
            listOf(" art ", "", "art", " blue"), listOf(listOf(1), listOf(-20, 280, 17), listOf(0, 255, 17, 88)))
        val record = ImageUiToCacheMapper.Base().map(42, 640, 480, "image-42", true, metadata)
        assertEquals(42, record.id)
        assertTrue(record.protected)
        assertEquals(FavoriteImageMetadata("Artist", "safe", "https://example.com/art", listOf("art", "blue"),
            listOf(listOf(0, 255, 17))), record.metadata)
    }

    @Serializable
    private data class LegacyFavorite(val id: Int, val netUrl: String, val width: Int, val height: Int,
        val created: Long, val protected: Boolean)
}
