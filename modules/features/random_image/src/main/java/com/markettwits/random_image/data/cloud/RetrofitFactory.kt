package com.markettwits.random_image.data.cloud

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface RetrofitFactory {
    fun <T : Any> create(service: Class<T>): T
    class Base(
        private val baseUrl: String,
        private val client: OkHttpClient,
        private val json: Json
    ) : RetrofitFactory {

        override fun <T : Any> create(service: Class<T>) = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .build()
            .create(service)
    }
    private companion object {
        const val CONTENT_TYPE = "application/json"
    }
}