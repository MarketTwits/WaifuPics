package com.markettwits.random_image.data.net

import com.markettwits.random_image.data.net.models.RandomImageCloud
import retrofit2.http.GET
import retrofit2.http.Query

interface NekoService {

@GET("v3/images/random")
suspend fun randomImage(
    @Query("rating") ageRating : List<String> = listOf("safe"),
    @Query("limit") limit : Int = 1
): RandomImageCloud

}