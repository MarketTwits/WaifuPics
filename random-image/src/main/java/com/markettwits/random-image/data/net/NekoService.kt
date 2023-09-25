package com.markettwits.waifupics.view.main.data.net

import com.markettwits.waifupics.view.main.data.net.models.AuthorCloud
import com.markettwits.waifupics.view.main.data.net.models.RandomImageCloud
import com.markettwits.waifupics.view.main.data.net.models.UserCloud
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NekoService {
    @GET("v2/images/random")
    suspend fun randomImage(
        @Query("filter[ageRating.in]") ageRating: String = "sfw"
    ): RandomImageCloud

    @GET("v2/users/{userId}")
    suspend fun fetchUserById(@Path("userId") userId: String): UserCloud

    @GET("v2/artists/{userId}")
    suspend fun fetchArtistById(@Path("userId") userId: String): AuthorCloud
}