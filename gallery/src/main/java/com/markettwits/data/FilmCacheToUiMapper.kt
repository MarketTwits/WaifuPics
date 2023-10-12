package com.markettwits.data

import com.markettwits.models.ImageFavoriteCache
import com.markettwits.presentation.ImageFavoriteUi

interface FavoriteImageCacheToUiMapper {
    fun map(item : ImageFavoriteCache) : ImageFavoriteUi
    class Base() : FavoriteImageCacheToUiMapper{
        override fun map(item: ImageFavoriteCache): ImageFavoriteUi {
            return ImageFavoriteUi.Base(
                id = item.id,
                imageUrl  = item.imageUrl,
                protected = item.protected
            )
        }
    }
}