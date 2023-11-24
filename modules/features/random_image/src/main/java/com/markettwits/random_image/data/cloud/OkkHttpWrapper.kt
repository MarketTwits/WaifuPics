package com.markettwits.random_image.data.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface OkkHttpWrapper {
    fun client(): OkHttpClient
    class Base() : OkkHttpWrapper {
        override fun client() = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
            .build()
    }
}