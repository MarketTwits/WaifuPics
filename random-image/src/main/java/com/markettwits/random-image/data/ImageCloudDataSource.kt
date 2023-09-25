package com.markettwits.waifupics.view.main.data

interface ImageCloudDataSource {
    fun fetchRandomImage(filters : Map<String, String>)
}