package com.markettwits.random_image.data.network

import com.markettwits.random_image.data.network.models.RandomImageCloud
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NekoService {

    @GET("v3/images/random")
    suspend fun randomImage(
        @Query("rating") ageRating : List<String> = listOf("safe"),
        @Query("limit") limit : Int = 1
    ): RandomImageCloud
    @POST("/v3/images/{id}/report")
    suspend fun report(
        @Path("id") id : Int
    )
    companion object{
        const val BASE_URL = "https://api.nekosapi.com/"
    }
}