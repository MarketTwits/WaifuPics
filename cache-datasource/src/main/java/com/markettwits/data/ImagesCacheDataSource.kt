package com.markettwits.data

import com.markettwits.core.CRUDApi
import com.markettwits.core.RealmDatabaseProvider
import com.markettwits.models.ImageFavoriteRealmCache
import io.realm.kotlin.ext.query

class ImagesCacheDataSource(
    private val realmProvider: RealmDatabaseProvider
) : CRUDApi.Write<ImageFavoriteRealmCache>,
    CRUDApi.Read.All<ImageFavoriteRealmCache> {
    private val realm = realmProvider.realm(setOf(ImageFavoriteRealmCache::class))
    override fun write(data: ImageFavoriteRealmCache) {
        realm.writeBlocking {
            copyToRealm(data)
        }
    }

    override fun read(): List<ImageFavoriteRealmCache> {
        val list = mutableListOf<ImageFavoriteRealmCache>()
        val result = realm.query(ImageFavoriteRealmCache::class).find()
        result.forEach { imageFavorite ->
            list.add(imageFavorite)
        }
        return list
    }

    fun delete(id: Long) {
        realm.writeBlocking {
            val item = query<ImageFavoriteRealmCache>(query = "_id == $0", id).first().find()
            try {
                item?.let { delete(it) }
            } catch (e: Exception) {
                throw RuntimeException("ImagesCacheDataSource#delete" + e.localizedMessage)
            }
        }
    }
}