package com.markettwits.image_action.api

interface ImageIntentAction {

    /**
     * Interface for sharing images.
     */
    interface ShareImage : ImageIntentAction {

        /**
         * Shares multiple images.
         *
         * @param imagePath List of image paths to share.
         */
        suspend fun shareImage(imagePath: List<String>)

        /**
         * Shares a single image.
         *
         * @param imagePath Path of the image to share.
         */
        suspend fun shareImage(imagePath: String)
    }

    /**
     * Interface for launching various image-related actions.
     */
    interface Action : ImageIntentAction {

        /**
         * Launches an intent to open the image with an appropriate app.
         *
         * @param imagePath Path of the image to open.
         */
        suspend fun launchOpenWith(imagePath: String)

        /**
         * Launches an intent to use the image as (e.g., wallpaper).
         *
         * @param imagePath Path of the image to use.
         */
        suspend fun launchUseAs(imagePath: String)
    }

    /**
     * Mutable interface combining ShareImage and Action interfaces.
     */
    interface Mutable : ShareImage, Action
}