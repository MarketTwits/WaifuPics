package com.markettwits.cloud_datasource.data.network

import com.markettwits.cloud_datasource.data.network.models.RandomImageCloud


interface NekoService {

    /**
     *
     * Retrieves a random image from the Nekos API based on specified age ratings and a limit.
     * @param ageRating
     * A list of age ratings (“safe”, “suggestive”, etc.) to filter the images.
     * @param limit The maximum number of images to retrieve.
     * @return
     * A RandomImageCloud object containing information about the randomly selected image.
     */
    suspend fun randomImage(
     ageRating : List<String> = listOf("safe"),
     limit : Int = 1
    ): RandomImageCloud

    /**
     * Reports an image with the specified ID to the Nekos API.
     * @param id The ID of the image to report.
     */
    suspend fun report(
       id : Int
    )
}