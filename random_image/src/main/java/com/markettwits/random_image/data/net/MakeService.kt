package com.markettwits.waifupics.view.main.data.net

import com.markettwits.random_image.data.net.NekoService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface MakeService {
    fun service(): NekoService
    abstract class Abstract() : MakeService {
        fun client() = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
            .build()
    }
    class Base() : Abstract() {
        override fun service() =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NekoService::class.java)

    }

    private companion object {
        const val BASE_URL = "https://api.nekosapi.com/"
    }

}