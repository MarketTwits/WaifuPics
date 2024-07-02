package com.markettwits.data.mapper


import com.markettwits.domain.ImageFavoriteCache
import kotlinx.datetime.Clock

interface ImageUiToCacheMapper {

    fun map( id: Int,
             width: Int,
             height: Int,
             url: String,
             isProtected: Boolean): ImageFavoriteCache

    class Base : ImageUiToCacheMapper {

        override fun map(
            id: Int,
            width: Int,
            height: Int,
            url: String,
            isProtected: Boolean
        ): ImageFavoriteCache =
           ImageFavoriteCache(
               id = id,
               width = width,
               height = height,
               netUrl = url,
               created = Clock.System.now().toEpochMilliseconds(),
               protected = isProtected
           )
    }
}