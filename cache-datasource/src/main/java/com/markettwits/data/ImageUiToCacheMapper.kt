package com.markettwits.data

import com.markettwits.models.ImageFavoriteRealmCache

interface ImageUiToCacheMapper {
    fun map(url: String, ageRating: String): ImageFavoriteRealmCache
    class Base : ImageUiToCacheMapper {
        override fun map(url: String, ageRating: String) =
            ImageFavoriteRealmCache().apply {
                imageUrl = url
                protected = ageRating == "explicit"
            }
    }
}