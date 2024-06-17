package com.markettwits.random_image.presentation.components.image

sealed class ImageState {
    data class Success(
        val id: Int,
        val networkUrl: String,
        val protected: Boolean,
    ) : ImageState()

    data object Loading : ImageState()

    data class Error(val message : String = "can't load image") : ImageState()
}