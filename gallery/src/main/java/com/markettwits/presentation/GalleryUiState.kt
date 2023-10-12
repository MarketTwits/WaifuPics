package com.markettwits.presentation

sealed interface GalleryUiState{

    data class Selected(private val selectedItems : List<ImageFavoriteUi>) : GalleryUiState
    data class Base(private val selectedItems : List<ImageFavoriteUi>) : GalleryUiState
    data object Initial : GalleryUiState
}