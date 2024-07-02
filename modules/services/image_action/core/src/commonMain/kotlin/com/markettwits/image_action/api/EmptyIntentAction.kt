package com.markettwits.image_action.api

class EmptyIntentAction : ImageIntentAction.Mutable {

    override suspend fun shareImage(imagePath: List<String>) = Unit

    override suspend fun shareImage(imagePath: String) = Unit

    override suspend fun launchOpenWith(imagePath: String) = Unit

    override suspend fun launchUseAs(imagePath: String) = Unit

    override suspend fun launchEditAs(imagePath: String) = Unit
}