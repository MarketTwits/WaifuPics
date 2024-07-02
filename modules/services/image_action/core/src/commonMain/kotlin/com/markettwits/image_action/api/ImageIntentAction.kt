package com.markettwits.image_action.api

interface ImageIntentAction {

    interface ShareImage : ImageIntentAction {

        suspend fun shareImage(imagePath: List<String>)

        suspend fun shareImage(imagePath: String)
    }

    interface Action : ImageIntentAction {

        suspend fun launchOpenWith(imagePath : String)

        suspend fun launchUseAs(imagePath : String)

        suspend fun launchEditAs(imagePath : String)
    }

    interface Mutable : ShareImage, Action
}