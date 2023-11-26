package com.markettwits.data.store

import com.markettwits.core.RealmDatabaseProvider
import com.markettwits.data.entities.ImageFavoriteRealmCache
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ImagesCacheDataSource(
    private val realmProvider: RealmDatabaseProvider,
) {
    private val realm = realmProvider.realm(setOf(ImageFavoriteRealmCache::class))
    fun write(data: ImageFavoriteRealmCache) {
        realm.writeBlocking {
            copyToRealm(data)
        }
    }

    fun read(): List<ImageFavoriteRealmCache> {
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

    fun delete(imageUrl: String) {
        realm.writeBlocking {
            val item =
                query<ImageFavoriteRealmCache>(query = "imageUrl == $0", imageUrl).first().find()
            try {
                item?.let { delete(it) }
            } catch (e: Exception) {
                throw RuntimeException("ImagesCacheDataSource#delete" + e.localizedMessage)
            }
        }
    }
    fun delete(id: List<Long>){
        realm.writeBlocking {
            id.forEach {
                val item =
                    query<ImageFavoriteRealmCache>(query = "_id == $0", it).first().find()
                try {
                    item?.let { delete(item) }
                } catch (e: Exception) {
                    throw RuntimeException("ImagesCacheDataSource#delete" + e.localizedMessage)
                }
            }
        }
    }


    fun hasImageWithUrl(imageUrl: String): Boolean {
        val item =
            realm.query<ImageFavoriteRealmCache>(query = "imageUrl == $0", imageUrl).count().find()
        return item > 0
    }

    fun observeFavoriteImages(): Flow<List<ImageFavoriteRealmCache>> {
        return realm.query<ImageFavoriteRealmCache>().asFlow().map { it.list }
    }

}