package com.markettwits.waifupics.view.main.data

import com.markettwits.waifupics.view.main.data.net.models.RandomImage
import retrofit2.http.GET

interface NekoService {
    @GET("v2/images/random")
    suspend fun randomImage() : RandomImage
}