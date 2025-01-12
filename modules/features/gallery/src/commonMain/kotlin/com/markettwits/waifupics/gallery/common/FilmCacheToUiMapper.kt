package com.markettwits.waifupics.gallery.common

import com.markettwits.cache.image.ImageFavoriteCache
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

interface FavoriteImageCacheToUiMapper {

    fun map(item: ImageFavoriteCache): ImageFavoriteUi

    fun mapTime(time: Long): String

    class Base : FavoriteImageCacheToUiMapper {

        override fun map(item: ImageFavoriteCache): ImageFavoriteUi {
            return if (item.protected) {
                ImageFavoriteUi.Protected(
                    id = item.id,
                    imageUrl = item.netUrl,
                    height = item.height,
                    width = item.width,
                    protected = item.protected,
                    created = mapTime(item.created)
                )
            } else {
                ImageFavoriteUi.Base(
                    id = item.id,
                    imageUrl = item.netUrl,
                    height = item.height,
                    width = item.width,
                    protected = item.protected,
                    created = mapTime(item.created)
                )
            }
        }

        @OptIn(FormatStringsInDatetimeFormats::class)
        override fun mapTime(time: Long): String {
            val instant = Instant.fromEpochMilliseconds(time)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val formatter = LocalDateTime.Format { byUnicodePattern(DATA_FORMAT) }
            return localDateTime.format(formatter)
        }
    }



    companion object {
       ///** example : 14 november 2023 year */
        const val DATA_FORMAT = "dd.MM.yyyy"
    }
}