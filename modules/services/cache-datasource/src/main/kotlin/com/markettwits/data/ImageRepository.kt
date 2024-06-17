package com.markettwits.data

import com.markettwits.data.mapper.ImageUiToCacheMapper
import com.markettwits.data.store.ImagesCacheDataSource
import com.markettwits.domain.ImageFavoriteCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ImageRepository {

    suspend fun observe() : Flow<List<ImageFavoriteCache>>

    suspend fun addToFavorite(networkUrl : String,protected : Boolean)

    suspend fun addOrDelete(url : String, protected: Boolean)

    suspend fun fetch(): List<ImageFavoriteCache>

    suspend fun deleteList(id : List<Long>,url : List<String>)

    suspend fun delete(id: Long, url : String)

    suspend fun delete(imageUrl : String)

    class Base(
        private val database: ImagesCacheDataSource,
        private val uiToCache: ImageUiToCacheMapper,
    ) : ImageRepository {
        override suspend fun fetch() = database.read().map { it.map() }

        override suspend fun deleteList(id: List<Long>,url : List<String>) {
            database.delete(id)
        }

        override suspend fun observe(): Flow<List<ImageFavoriteCache>> {
            return  database.observeFavoriteImages().map { it.map { it.map() } }
        }

        override suspend fun addToFavorite(networkUrl : String, protected: Boolean) {
            database.write(uiToCache.map(networkUrl, networkUrl, protected))
            database.observeFavoriteImages()
        }

        override suspend fun addOrDelete(url : String, protected: Boolean) {
            if (database.hasImageWithUrl(url)) {
                database.delete(url)
                delete(url)
            } else {
                addToFavorite(url, protected)
            }
        }

        override suspend fun delete(id: Long, url : String) {
            database.delete(id)
        }

        override suspend fun delete(imageUrl: String) {
            database.delete(imageUrl)
        }
    }
}