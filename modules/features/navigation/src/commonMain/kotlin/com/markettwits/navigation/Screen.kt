package com.markettwits.navigation

import kotlinx.serialization.Serializable


interface Screen  {

    fun route(): String

    @Serializable
    abstract class Abstract(private val route: String) : Screen {
        override fun route() = route
    }

    @Serializable
    object Main : Abstract(ROUTE_MAIN)

    @Serializable
    object Gallery : Abstract(ROUTE_GALLERY)

    @Serializable
    object About : Abstract(ROUTE_ABOUT)

    @Serializable
    object GalleryItem : Abstract(ROUTE_GALLERY_IMAGE)

    companion object {
        private const val ROUTE_GALLERY_IMAGE = "detail_screen"
        private const val ROUTE_MAIN = "main_screen"
        private const val ROUTE_GALLERY = "gallery_screen"
        private const val ROUTE_ABOUT = "about_screen"
    }
}
