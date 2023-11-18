package com.markettwits.presentation.list

import com.markettwits.presentation.detail.ImageFavoriteUi

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