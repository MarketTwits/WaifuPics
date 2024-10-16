package com.markettwits.waifupics.cloud.http

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

interface HttpClientProvider {

    fun provide(loggerEnabled: Boolean = false): HttpClient

    fun json(): Json
}