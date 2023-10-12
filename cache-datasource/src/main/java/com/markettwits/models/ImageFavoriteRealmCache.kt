package com.markettwits.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlin.random.Random


class ImageFavoriteRealmCache : RealmObject {
    @PrimaryKey
    var _id: Long = Random.nextLong()
    var imageUrl: String = ""
    var protected: Boolean = false
    fun map() = ImageFavoriteCache(_id, imageUrl, protected)
}

data class ImageFavoriteCache(
    val id: Long,
    val imageUrl: String,
    val protected: Boolean
)