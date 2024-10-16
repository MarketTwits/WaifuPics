package com.markettwits.waifupics.random.cloud.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistCloud(
    @SerialName("aliases")
    val aliases: List<String>,
    @SerialName("id")
    val id: Int,
    @SerialName("image_url")
    val image_url: String?,
    @SerialName("links")
    val links: List<String>,
    @SerialName("name")
    val name: String,
)