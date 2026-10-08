package com.markettwits.waifupics.random.cloud.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RandomImageItemCloud(
    @SerialName("artist_name") val artistName: String?,
    @SerialName("color_dominant") val colorDominant: List<Int>,
    @SerialName("color_palette") val colorPalette: List<List<Int>>,
    @SerialName("id") val id: Int,
    @SerialName("rating") val rating: String,
    @SerialName("source_url") val sourceUrl: String?,
    @SerialName("tags") val tags: List<String>,
    @SerialName("url") val url: String
)