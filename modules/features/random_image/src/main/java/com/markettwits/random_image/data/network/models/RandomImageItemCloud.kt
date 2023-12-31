package com.markettwits.random_image.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class RandomImageItemCloud(
    val color_dominant: List<Int>,
    val color_palette: List<List<Int>>,
    val id: Int,
    val artist : ArtistCloud?,
    val id_v2: String,
    val sample_url: String,
    val image_url : String,
    val source: String?,
    val rating : String
){
    @Serializable
    data class ArtistCloud(
        val aliases: List<String>,
        val id: Int,
        val image_url: String?,
        val links: List<String>,
        val name: String,
    )
    data class TagCloud(val tag : String)

}
@Serializable
data class RandomImageCloud(val items : List<RandomImageItemCloud>)