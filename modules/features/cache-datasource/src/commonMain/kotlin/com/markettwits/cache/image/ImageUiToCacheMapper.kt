package com.markettwits.cache.image

import kotlin.time.ExperimentalTime


interface ImageUiToCacheMapper {

    fun map(
        id: Int,
        width: Int,
        height: Int,
        url: String,
        isProtected: Boolean,
        metadata: FavoriteImageMetadata? = null
    ): ImageFavoriteCache

    class Base : ImageUiToCacheMapper {

        @OptIn(ExperimentalTime::class)
        override fun map(
            id: Int,
            width: Int,
            height: Int,
            url: String,
            isProtected: Boolean,
            metadata: FavoriteImageMetadata?
        ): ImageFavoriteCache =
            ImageFavoriteCache(
                id = id,
                width = width,
                height = height,
                netUrl = url,
                created = kotlin.time.Clock.System.now().toEpochMilliseconds(),
                protected = isProtected,
                metadata = metadata?.normalized()
            )
    }
}
