package com.markettwits.cloud_datasource.presentation.random_image.components.image

import androidx.compose.runtime.Stable

@Stable
sealed class ImageState {

    @Stable
    data class Success(
        val id: Int,
        val networkUrl: String,
        val width: Int,
        val height: Int,
        val protected: Boolean,
    ) : ImageState()

    @Stable
    data object Loading : ImageState()

    @Stable
    data class Error(val message: String = "can't load image") : ImageState()
}