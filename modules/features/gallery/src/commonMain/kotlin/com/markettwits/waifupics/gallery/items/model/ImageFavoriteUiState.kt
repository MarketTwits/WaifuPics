package com.markettwits.waifupics.gallery.items.model

import androidx.compose.runtime.Stable

@Stable
sealed class ImageFavoriteUiState(val items: List<ImageFavoriteUi>) {

    @Stable
    data class Base(val imageFavoriteUi: List<ImageFavoriteUi>) :
        ImageFavoriteUiState(imageFavoriteUi)

    @Stable
    data object Empty : ImageFavoriteUiState(emptyList())
}