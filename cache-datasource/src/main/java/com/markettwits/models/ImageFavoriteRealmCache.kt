package com.markettwits.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlin.random.Random


class ImageFavoriteRealmCache : RealmObject {
    @PrimaryKey
    var _id: Long = Random.nextLong()
    var imageUrl: String = ""
    var localUrl : String = ""
    var createdTime : Long = 0L
    var protected: Boolean = false
    fun map() = ImageFavoriteCache(_id, imageUrl, localUrl, createdTime, protected)
}

data class ImageFavoriteCache(
    val id: Long,
    val netUrl: String,
    val localUrl : String,
    val created : Long,
    val protected: Boolean
)