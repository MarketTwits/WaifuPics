package com.markettwits.cache.image

import kotlinx.serialization.Serializable

/** Optional addition to the existing favourites file; every field also supports partial metadata. */
@Serializable
data class FavoriteImageMetadata(
    val artistName: String? = null,
    val ageRating: String? = null,
    val sourceUrl: String? = null,
    val tags: List<String> = emptyList(),
    val colorPalette: List<List<Int>> = emptyList()
) {
    fun normalized() = copy(
        artistName = artistName?.trim()?.takeIf(String::isNotEmpty),
        ageRating = ageRating?.trim()?.takeIf(String::isNotEmpty),
        sourceUrl = sourceUrl?.trim()?.takeIf(String::isNotEmpty),
        tags = tags.map(String::trim).filter(String::isNotEmpty).distinct(),
        colorPalette = colorPalette.filter { it.size >= 3 }
            .map { rgb -> rgb.take(3).map { it.coerceIn(0, 255) } }.distinct()
    )
}
