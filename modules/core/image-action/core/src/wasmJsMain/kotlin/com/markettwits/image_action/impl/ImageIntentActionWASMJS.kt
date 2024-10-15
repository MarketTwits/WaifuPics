package com.markettwits.image_action.impl

import com.markettwits.image_action.api.ImageIntentAction
import com.markettwits.image_action.api.ImageLoader

class ImageIntentActionWASMJS(private val imageLoader : ImageLoader) : ImageIntentAction.Mutable {

    override suspend fun shareImage(imagePath: List<String>) {
        imageLoader.saveToGallery(imagePath.first())
    }

    override suspend fun shareImage(imagePath: String) {
        imageLoader.saveToGallery(imagePath)
    }

    override suspend fun launchOpenWith(imagePath: String) {
        imageLoader.saveToGallery(imagePath)
    }

    override suspend fun launchUseAs(imagePath: String) {
        imageLoader.saveToGallery(imagePath)
    }
}