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
    open val savedAt: Long? = null,
) {
    data class Base(
        override val id: Int,
        override val imageUrl: String,
        override val width: Int,
        override val height: Int,
        override val created: String,
        override val protected: Boolean,
        override val savedAt: Long? = null
    ) : ImageFavoriteUi(id, imageUrl, width, height, created, protected, savedAt)

    data class Protected(
        override val id: Int,
        override val imageUrl: String,
        override val width: Int,
        override val height: Int,
        override val created: String,
        override val protected: Boolean,
        override val savedAt: Long? = null
    ) : ImageFavoriteUi(id, imageUrl, width, height, created, protected, savedAt)

    data object Initial : ImageFavoriteUi(0, "", 0, 0, "", false)
}

