package com.markettwits.waifupics.view.main.data.net

import com.markettwits.waifupics.view.main.data.NekoService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MakeService {
    fun service(): NekoService
    class Base() : MakeService {

        override fun service() =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NekoService::class.java)
    }

    private companion object {
        const val BASE_URL = "https://api.nekosapi.com/"
    }

}