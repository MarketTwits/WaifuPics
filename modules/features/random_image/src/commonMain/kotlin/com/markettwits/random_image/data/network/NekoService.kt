package com.markettwits.random_image.data.network

import com.markettwits.random_image.data.network.models.RandomImageCloud
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query


interface NekoService {

    @Headers("Accept: application/json")
    @GET("v3/images/random")
    suspend fun randomImage(
        @Query("rating") ageRating : List<String> = listOf("safe"),
        @Query("limit") limit : Int = 1
    ): RandomImageCloud

    @Headers("Accept: application/json")
    @POST("/v3/images/{id}/report")
    suspend fun report(
        @Path("id") id : Int
    )

    companion object {
        const val BASE_URL = "https://api.nekosapi.com/"
    }
}