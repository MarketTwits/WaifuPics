package com.markettwits.waifupics.gallery.items.model

import androidx.compose.runtime.Immutable

@Immutable
sealed class ImageFavoriteUi(
    open val id: Int,
    open val imageUrl: String,
    open val width: Int,
    open val height: Int,
    open val created: String,
    open val protected: Boolean,
) {
    data class Base(
        override val id: Int,
        override val imageUrl: String,
        override val width: Int,
        override val height: Int,
        override val created: String,
        override val protected: Boolean
    ) : ImageFavoriteUi(id, imageUrl, width, height, created, protected)

    data class Protected(
        override val id: Int,
        override val imageUrl: String,
        override val width: Int,
        override val height: Int,
        override val created: String,
        override val protected: Boolean
    ) : ImageFavoriteUi(id, imageUrl, width, height, created, protected)

    data object Initial : ImageFavoriteUi(0, "", 0, 0, "", false)
}

