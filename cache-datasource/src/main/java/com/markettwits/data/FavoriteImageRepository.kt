package com.markettwits.data

import com.markettwits.models.ImageFavoriteCache

interface FavoriteImageRepository {
    suspend fun add(url : String, ageRating : String)
    suspend fun fetch() : List<ImageFavoriteCache>
    suspend fun delete(id : Long)
    class Base(
        private val database : ImagesCacheDataSource,
        private val uiToCache : ImageUiToCacheMapper,
    ) : FavoriteImageRepository {
        override suspend fun add(url : String, ageRating : String) {
           database.write(uiToCache.map(url, ageRating))
        }
        override suspend fun fetch() = database.read().map { it.map() }
        override suspend fun delete(id : Long) {
           database.delete(id)
        }
    }
}