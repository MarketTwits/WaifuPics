package com.markettwits.cache.image

import kotlinx.coroutines.flow.Flow

interface ImageCacheRepository {

    fun observe(): Flow<List<ImageFavoriteCache>>

    suspend fun fetch(): List<ImageFavoriteCache>

    suspend fun addToFavorite(
        id: Int,
        width: Int,
        height: Int,
        url: String,
        isProtected: Boolean,
        metadata: FavoriteImageMetadata? = null
    )

    suspend fun addOrDelete(
        id: Int,
        width: Int,
        height: Int,
        url: String,
        isProtected: Boolean,
        metadata: FavoriteImageMetadata? = null
    )

    suspend fun deleteList(id: List<Int>, url: List<String>)

    suspend fun delete(id: Int)

    suspend fun delete(imageUrl: String)

    suspend fun removeForUndo(ids: Set<Int>): FavoriteImageRemoval

    suspend fun restoreRemoved(removals: List<FavoriteImageRemoval>)

    class Base(
        private val database: ImagesCacheDataSource,
        private val uiToCache: ImageUiToCacheMapper,
    ) : ImageCacheRepository {

        override suspend fun removeForUndo(ids: Set<Int>) = database.removeForUndo(ids)

        override suspend fun restoreRemoved(removals: List<FavoriteImageRemoval>) = database.restoreRemoved(removals)

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
            isProtected: Boolean,
            metadata: FavoriteImageMetadata?
        ) {
            database.write(uiToCache.map(id, width, height, url, isProtected, metadata))
        }

        override suspend fun addOrDelete(
            id: Int,
            width: Int,
            height: Int,
            url: String,
            isProtected: Boolean,
            metadata: FavoriteImageMetadata?
        ) {
            if (database.hasImageWithUrl(url)) {
                delete(url)
            } else {
                addToFavorite(id, width, height, url, isProtected, metadata)
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
