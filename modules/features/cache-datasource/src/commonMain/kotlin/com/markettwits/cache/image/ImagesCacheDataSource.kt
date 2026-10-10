package com.markettwits.cache.image

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.extensions.updatesOrEmpty
import kotlinx.coroutines.flow.Flow

class ImagesCacheDataSource(
    private val cache: KStore<List<ImageFavoriteCache>>,
) {

    suspend fun write(data: ImageFavoriteCache) {
        cache.update {
            it.orEmpty().plus(data)
        }
    }

    suspend fun getAll(): List<ImageFavoriteCache> = cache.get() ?: emptyList()

    suspend fun delete(id: Int) {
        delete(listOf(id))
    }

    suspend fun delete(imageUrl: String) {
        cache.update { it.orEmpty().filterNot { image -> image.netUrl == imageUrl } }
    }

    suspend fun delete(id: List<Int>) {
        val ids = id.toSet()
        cache.update { it.orEmpty().filterNot { image -> image.id in ids } }
    }

    suspend fun removeForUndo(ids: Set<Int>): FavoriteImageRemoval {
        var removal = FavoriteImageRemoval(emptyList(), emptyList())
        cache.update { stored ->
            val before = stored.orEmpty()
            removal = FavoriteImageRemoval(before, before.filter { it.id in ids })
            before.filterNot { it.id in ids }
        }
        return removal
    }

    suspend fun restoreRemoved(removals: List<FavoriteImageRemoval>) {
        cache.update { restoreFavoriteImages(it.orEmpty(), removals) }
    }

    suspend fun hasImageWithUrl(imageUrl: String): Boolean {
        val list = cache.get().orEmpty()
        return list.any { it.netUrl == imageUrl }
    }

    fun observeFavoriteImages(): Flow<List<ImageFavoriteCache>> = cache.updatesOrEmpty
}
