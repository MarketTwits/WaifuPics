package com.markettwits.data

import com.markettwits.domain.ImageFavoriteCache
import com.markettwits.presentation.screens.detail.ImageFavoriteUi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface FavoriteImageCacheToUiMapper {
    fun map(item : ImageFavoriteCache) : ImageFavoriteUi
    fun mapTime(time : Long) : String
    class Base : FavoriteImageCacheToUiMapper{
        override fun map(item: ImageFavoriteCache): ImageFavoriteUi {
            return if (item.protected) {
                ImageFavoriteUi.Protected(
                    id = item.id,
                    imageUrl = item.localUrl,
                    protected = item.protected,
                    created = mapTime(item.created)
                )
            }else{
                ImageFavoriteUi.Base(
                    id = item.id,
                    imageUrl = item.localUrl,
                    protected = item.protected,
                    created = mapTime(item.created))
            }
        }
        override fun mapTime(time: Long): String {
            val date = Date(time * MILLS_IN_SECOND)
            val format = SimpleDateFormat(DATA_FORMAT, Locale.US)
            return format.format(date).lowercase(Locale.getDefault())
        }
    }
    private companion object {
        /**
         * example :
         * 14 november 2023 year
         */
        const val DATA_FORMAT = "d MMMM yyyy 'year'"
        const val MILLS_IN_SECOND = 1000
    }
}