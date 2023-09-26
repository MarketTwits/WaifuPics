package com.markettwits.database

import com.markettwits.data.ImageUiToCacheMapper
import com.markettwits.models.ImageFavoriteCache

interface FavoriteImageRepository {
    suspend fun add(url : String, ageRating : String)
    suspend fun fetch() : List<ImageFavoriteCache>
    class Base(
        private val database : ImagesCacheDataSource,
        private val uiToCache : ImageUiToCacheMapper,
    ) : FavoriteImageRepository {
        override suspend fun add(url : String, ageRating : String) {
           database.write(uiToCache.map(url, ageRating))
        }
        override suspend fun fetch() = database.read().map { it.map() }

    }
}