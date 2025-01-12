package com.markettwits.waifupics.random.model

import androidx.compose.runtime.Stable

@Stable
sealed interface RandomImageState {

    @Stable
    data object Initial : RandomImageState

    @Stable
    data object Progress : RandomImageState

    @Stable
    data class Error(val message: String) : RandomImageState

    @Stable
    data class Success(
        val id: Int,
        val imageUrl: String,
        val colorPalette: List<List<Int>>,
        val imageData: ImageSourceUi,
    ) : RandomImageState
}