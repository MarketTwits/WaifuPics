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
    companion object {
        private const val ROUTE_MAIN = "main_screen"
        private const val ROUTE_GALLERY = "gallery_screen"
        private const val ROUTE_ABOUT = "about_screen"
    }
}