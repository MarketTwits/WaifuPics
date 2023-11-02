package com.markettwits.navigation

interface Graph {
    fun graph() : String
    object Main : Graph {
        override fun graph() = "MAIN"
    }
    object Gallery : Graph{
        override fun graph() = "GALLERY"
    }
    object GalleryDetails : Graph{
        override fun graph() = "GALLERY_DETAILS"
    }
    object Root : Graph{
        override fun graph() = "ROOT"
    }
}