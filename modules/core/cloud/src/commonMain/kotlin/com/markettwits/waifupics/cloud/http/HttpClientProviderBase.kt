package com.markettwits.waifupics.cloud.http

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class HttpClientProviderBase(
    private val json: Json,
    private val baseUrl: String
) : HttpClientProvider {

    override fun provide(loggerEnabled: Boolean) = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) {
            json(json)
        }
        if (loggerEnabled) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HttpClient: $message")
                    }
                }
                level = LogLevel.ALL
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 100000
        }
        defaultRequest {
            url(baseUrl)
        }
    }

    override fun json(): Json = json
}
