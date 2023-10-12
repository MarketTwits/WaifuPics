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
        fun routeId(imageId : String) = "$ROUTE_FOR_ARGS/$imageId"
    }

    companion object {
         const val ROUTE_FOR_ARGS = "gallery_item"
         const val ROUTE_GALLERY_IMAGE = "$ROUTE_FOR_ARGS/{imageId}"
        private const val ROUTE_MAIN = "main_screen"
        private const val ROUTE_GALLERY = "gallery_screen"
        private const val ROUTE_ABOUT = "about_screen"
    }
}