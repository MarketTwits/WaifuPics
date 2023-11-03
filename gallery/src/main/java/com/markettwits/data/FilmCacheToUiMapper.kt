package com.markettwits.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.markettwits.models.ImageFavoriteCache
import com.markettwits.presentation.detail.ImageFavoriteUi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface FavoriteImageCacheToUiMapper {
    fun map(item : ImageFavoriteCache) : ImageFavoriteUi
    fun mapTime(time : Long) : String
    class Base() : FavoriteImageCacheToUiMapper{
        @RequiresApi(Build.VERSION_CODES.O)
        override fun map(item: ImageFavoriteCache): ImageFavoriteUi {
            return ImageFavoriteUi.Base(
                id = item.id,
                imageUrl  = item.localUrl,
                protected = item.protected,
                created = mapTime(item.created)
            )
        }
        @RequiresApi(Build.VERSION_CODES.O)
        override fun mapTime(time: Long): String {
            val localDateTime = LocalDateTime.ofEpochSecond(time, 0, java.time.ZoneOffset.UTC)
            val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'year'")
            return localDateTime.format(formatter)
        }
    }
}