package com.markettwits.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class ImageFavoriteRealmCache : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var imageUrl: String = ""
    var protected: Boolean = false
    fun map() = ImageFavoriteCache(imageUrl, protected)
}

data class ImageFavoriteCache(
     val imageUrl: String,
     val protected: Boolean
)