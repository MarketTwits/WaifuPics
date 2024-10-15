package com.markettwits.waifupics.random_image.image.model


interface RandomImageState {

    object Initial : RandomImageState

    object Progress : RandomImageState

    data class Error(val message: String) : RandomImageState

    interface Success : RandomImageState {

        data class EmptyAuthor(
            val id: Int,
            val imageUrl: String,
            val colorPalette: List<List<Int>>,
            val imageData: ImageSourceUi,
        ) : Success

        data class WithAuthor(
            val id: Int,
            val imageUrl: String,
            val colorPalette: List<List<Int>>,
            val imageData: ImageSourceUi,
            val author: AuthorUi,
        ) : Success

    }

}