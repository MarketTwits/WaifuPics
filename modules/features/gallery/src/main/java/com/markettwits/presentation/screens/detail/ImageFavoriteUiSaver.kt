package com.markettwits.presentation.screens.detail

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import com.markettwits.presentation.screens.ImageFavoriteUi

object ImageFavoriteUiSaver: Saver<ImageFavoriteUi, Map<String, Any>> {
    override fun restore(value: Map<String, Any>): ImageFavoriteUi {
        val id = value["id"] as Long
        val created = value["created"] as String
        val imageUrl = value["imageUrl"] as String
        val protected = value["protected"] as Boolean
        return ImageFavoriteUi.Base(id, imageUrl, created, protected)
    }

    override fun SaverScope.save(value: ImageFavoriteUi): Map<String, Any> {
        return mapOf(
            "id" to value.id(),
            "created" to value.created(),
            "imageUrl" to value.imageUrl(),
            "protected" to value.protected()
        )
    }
}