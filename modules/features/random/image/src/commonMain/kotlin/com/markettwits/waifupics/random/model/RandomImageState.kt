package com.markettwits.waifupics.random.model

import androidx.compose.runtime.Stable


@Stable
sealed interface RandomImageState {

    @Stable
    object Initial : RandomImageState

    @Stable
    object Progress : RandomImageState

    @Stable
    data class Error(val message: String) : RandomImageState

    sealed interface Success : RandomImageState {

        @Stable
        data class EmptyAuthor(
            val id: Int,
            val imageUrl: String,
            val colorPalette: List<List<Int>>,
            val imageData: ImageSourceUi,
        ) : Success

        @Stable
        data class WithAuthor(
            val id: Int,
            val imageUrl: String,
            val colorPalette: List<List<Int>>,
            val imageData: ImageSourceUi,
            val author: AuthorUi,
        ) : Success

    }

}