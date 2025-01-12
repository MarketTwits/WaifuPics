package com.markettwits.waifupics.random.cloud.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RandomImageItemCloud(
    @SerialName("artist_name") val artist_name: String?,
    @SerialName("color_dominant") val color_dominant: List<Int>,
    @SerialName("color_palette") val color_palette: List<List<Int>>,
    @SerialName("id") val id: Int,
    @SerialName("rating") val rating: String,
    @SerialName("source_url") val source_url: String?,
    @SerialName("tags") val tags: List<String>,
    @SerialName("url") val url: String
)