package com.markettwits.data.mapper


import com.markettwits.data.entities.ImageFavoriteRealmCache
import java.time.LocalDateTime

interface ImageUiToCacheMapper {

    fun map(networkUrl: String,storageUrl : String, ageRating: Boolean): ImageFavoriteRealmCache

    class Base : ImageUiToCacheMapper {

        override fun map(networkUrl: String, storageUrl : String, ageRating: Boolean) =
            ImageFavoriteRealmCache().apply {
                imageUrl = networkUrl
                localUrl = storageUrl
                createdTime = now()
                protected = ageRating
            }

        private fun now() : Long{
            return LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.UTC)
        }
    }
}