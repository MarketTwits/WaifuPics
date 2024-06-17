package com.markettwits.domain


data class ImageFavoriteCache(
    val id: Long,
    val netUrl: String,
    val localUrl : String,
    val created : Long,
    val protected: Boolean
)