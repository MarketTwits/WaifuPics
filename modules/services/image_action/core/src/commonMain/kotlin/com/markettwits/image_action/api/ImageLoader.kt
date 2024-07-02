package com.markettwits.image_action.api

interface ImageLoader {

    suspend fun saveToGallery(imageUrl:String)

}