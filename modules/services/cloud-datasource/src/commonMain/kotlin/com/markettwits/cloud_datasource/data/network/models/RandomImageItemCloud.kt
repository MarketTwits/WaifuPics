package com.markettwits.cloud_datasource.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomImageItemCloud(
    @SerialName("color_dominant")
    val color_dominant: List<Int>,
    @SerialName("color_palette")
    val color_palette: List<List<Int>>,
    @SerialName("id")
    val id: Int,
    @SerialName("artist")
    val artist : ArtistCloud?,
    @SerialName("id_v2")
    val id_v2: String,
    @SerialName("sample_url")
    val sample_url: String,
    @SerialName("image_url")
    val image_url : String,
    @SerialName("source")
    val source: String?,
    @SerialName("rating")
    val rating : String
){
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
}

@Serializable
data class RandomImageCloud(
    @SerialName("items")
    val items : List<RandomImageItemCloud>
)