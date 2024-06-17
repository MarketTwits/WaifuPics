package com.markettwits.random_image.data.cloud

import com.markettwits.random_image.data.network.NekoService
import com.markettwits.random_image.data.network.createNekoService
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface RetrofitFactory {

    fun <T : Any> create(): NekoService

    class Base(
        private val baseUrl: String,
        private val json: Json
    ) : RetrofitFactory {
        override fun <T : Any> create() = ktorfit {
            baseUrl(baseUrl)
            httpClient(HttpClient {
                install(ContentNegotiation) {
                    json(json)
                }
            })
        }.createNekoService()
    }
}