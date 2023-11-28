package com.markettwits.presentation.screens

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
sealed class ImageFavoriteUi(
    open val id: Long,
    open val imageUrl: String,
    open val created: String,
    open val protected: Boolean,
) : Parcelable {
    fun toBase(): Base  = Base(id, imageUrl, created, protected)
    data class Base(
        override val id: Long,
        override val imageUrl: String,
        override val created: String,
        override val protected: Boolean): ImageFavoriteUi(id, imageUrl, created, protected)
    data class Protected(
        override val id: Long,
        override val imageUrl: String,
        override val created: String,
        override val protected: Boolean): ImageFavoriteUi(id, imageUrl, created, protected)
    data object Initial : ImageFavoriteUi(0, "", "", false)
}