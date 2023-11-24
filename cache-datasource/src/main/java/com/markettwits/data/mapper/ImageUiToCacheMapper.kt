package com.markettwits.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.markettwits.data.entities.ImageFavoriteRealmCache
import java.time.LocalDateTime

interface ImageUiToCacheMapper {
    fun map(networkUrl: String,storageUrl : String, ageRating: Boolean): ImageFavoriteRealmCache
    class Base : ImageUiToCacheMapper {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun map(networkUrl: String, storageUrl : String, ageRating: Boolean) =
            ImageFavoriteRealmCache().apply {
                imageUrl = networkUrl
                localUrl = storageUrl
                createdTime = now()
                protected = ageRating
            }
        @RequiresApi(Build.VERSION_CODES.O)
        private fun now() : Long{
            val time = LocalDateTime.now()
            val t = time.toEpochSecond(java.time.ZoneOffset.UTC)
            return t
        }
    }
}