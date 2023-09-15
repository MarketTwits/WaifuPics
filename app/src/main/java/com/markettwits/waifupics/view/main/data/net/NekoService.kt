package com.markettwits.waifupics.view.main.data.net

import com.markettwits.waifupics.view.main.data.net.models.RandomImageCloud
import retrofit2.http.GET

interface NekoService {
    @GET("v2/images/random")
    suspend fun randomImage() : RandomImageCloud
}