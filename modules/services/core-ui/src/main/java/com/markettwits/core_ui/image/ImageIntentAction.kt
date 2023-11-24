package com.markettwits.core_ui.image

interface ImageIntentAction {
    interface ShareImage : ImageIntentAction{
        suspend fun shareImage(imagePath: String)
    }
    interface Action : ImageIntentAction{
        suspend fun launchOpenWith(imagePath : String)
        suspend fun launchUseAs(imagePath : String)
        suspend fun launchEditAs(imagePath : String)
    }
    interface Mutable : ShareImage, Action
}