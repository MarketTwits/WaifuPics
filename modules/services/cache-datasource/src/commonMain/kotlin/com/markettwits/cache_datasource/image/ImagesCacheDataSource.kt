package com.markettwits.cache_datasource.image

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.extensions.getOrEmpty
import io.github.xxfast.kstore.extensions.updatesOrEmpty
import kotlinx.coroutines.flow.Flow

class ImagesCacheDataSource(
    private val cache: KStore<List<ImageFavoriteCache>>,
) {

    suspend fun write(data: ImageFavoriteCache) {
        cache.update {
            it?.plus(data)
        }
    }

    suspend fun getAll(): List<ImageFavoriteCache> = cache.get() ?: emptyList()

    suspend fun delete(id: Int) {
        val items = cache.getOrEmpty()
        cache.update {
            val currentItem = items.first { firstItem -> firstItem.id == id }
            it?.minus(currentItem)
        }
    }

    suspend fun delete(imageUrl: String) {
        val items = cache.getOrEmpty()
        cache.update {
            val currentItem = items.first { firstItem -> firstItem.netUrl == imageUrl }
            it?.minus(currentItem)
        }
    }

    suspend fun delete(id: List<Int>) {
        id.forEach { currentId ->
            delete(currentId)
        }
    }

    suspend fun hasImageWithUrl(imageUrl: String): Boolean {
        val list = cache.getOrEmpty()
        return list.any { it.netUrl == imageUrl }
    }

    fun observeFavoriteImages(): Flow<List<ImageFavoriteCache>> = cache.updatesOrEmpty
}

