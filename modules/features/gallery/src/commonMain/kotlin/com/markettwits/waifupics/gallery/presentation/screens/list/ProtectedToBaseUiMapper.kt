package com.markettwits.waifupics.gallery.presentation.screens.list

import com.markettwits.waifupics.gallery.presentation.screens.ImageFavoriteUi

interface ProtectedToBaseUiMapper {
    fun map(list : List<ImageFavoriteUi>) : List<ImageFavoriteUi>
    class Base : ProtectedToBaseUiMapper {
        override fun map(list: List<ImageFavoriteUi>): List<ImageFavoriteUi> {
            return list.map { imageFavorite ->
                imageFavorite.toBase()
            }
        }
    }
}