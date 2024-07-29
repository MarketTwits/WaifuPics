package com.markettwits.cache_datasource.image

import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun observe(): Flow<List<ImageFavoriteCache>>

    suspend fun fetch(): List<ImageFavoriteCache>

    suspend fun addToFavorite(
        id: Int,
        width: Int,
        height: Int,
        url: String,
        isProtected: Boolean
    )

    suspend fun addOrDelete(
        id: Int,
        width: Int,
        height: Int,
        url: String,
        isProtected: Boolean
    )

    suspend fun deleteList(id: List<Int>, url: List<String>)

    suspend fun delete(id: Int)

    suspend fun delete(imageUrl: String)

    class Base(
        private val database: ImagesCacheDataSource,
        private val uiToCache: ImageUiToCacheMapper,
    ) : ImageRepository {

        override suspend fun deleteList(id: List<Int>, url: List<String>) {
            database.delete(id)
        }

        override fun observe(): Flow<List<ImageFavoriteCache>> = database.observeFavoriteImages()

        override suspend fun fetch(): List<ImageFavoriteCache> = database.getAll()

        override suspend fun addToFavorite(
            id: Int,
            width: Int,
            height: Int,
            url: String,
            isProtected: Boolean
        ) {
            database.write(uiToCache.map(id, width, height, url, isProtected))
        }

        override suspend fun addOrDelete(
            id: Int,
            width: Int,
            height: Int,
            url: String,
            isProtected: Boolean
        ) {
            if (database.hasImageWithUrl(url)) {
                delete(url)
            } else {
                addToFavorite(id, width, height, url, isProtected)
            }
        }

        override suspend fun delete(id: Int) {
            database.delete(id)
        }

        override suspend fun delete(imageUrl: String) {
            database.delete(imageUrl)
        }
    }
}