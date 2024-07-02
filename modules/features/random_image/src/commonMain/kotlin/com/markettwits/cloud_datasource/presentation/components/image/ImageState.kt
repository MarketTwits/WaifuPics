package com.markettwits.cloud_datasource.presentation.components.image

sealed class ImageState {

    data class Success(
        val id: Int,
        val networkUrl: String,
        val width: Int,
        val height: Int,
        val protected: Boolean,
    ) : ImageState()

    data object Loading : ImageState()

    data class Error(val message: String = "can't load image") : ImageState()
}