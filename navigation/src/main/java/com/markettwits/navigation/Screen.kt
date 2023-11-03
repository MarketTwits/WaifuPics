package com.markettwits.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface Screen : Parcelable {
    fun route(): String
    abstract class Abstract(private val route: String) : Screen, Parcelable {
        override fun route() = route
    }
    @Parcelize
    object Main : Abstract(ROUTE_MAIN)

    @Parcelize
    object Gallery : Abstract(ROUTE_GALLERY)

    @Parcelize
    object About : Abstract(ROUTE_ABOUT)
    @Parcelize
    object GalleryItem : Abstract(ROUTE_GALLERY_IMAGE){
//        fun routArgument() = ARG_GALLERY_ITEM
//        fun routeId(imageId : String) = "$ROUTE_FOR_ARGS/$imageId"
    }

    companion object {
        private const val ARG_GALLERY_ITEM = "imageId"
        private const val ROUTE_FOR_ARGS = "gallery_item"
        //private const val ROUTE_GALLERY_IMAGE = "$ROUTE_FOR_ARGS/{$ARG_GALLERY_ITEM}"
        private const val ROUTE_GALLERY_IMAGE = "detail_screen"
        private const val ROUTE_MAIN = "main_screen"
        private const val ROUTE_GALLERY = "gallery_screen"
        private const val ROUTE_ABOUT = "about_screen"
    }
}
