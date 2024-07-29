package com.markettwits.cache_datasource.image

import kotlinx.serialization.Serializable

@Serializable
data class ImageFavoriteCache(
    val id: Int,
    val netUrl: String,
    val width : Int,
    val height : Int,
    val created : Long,
    val protected: Boolean
)