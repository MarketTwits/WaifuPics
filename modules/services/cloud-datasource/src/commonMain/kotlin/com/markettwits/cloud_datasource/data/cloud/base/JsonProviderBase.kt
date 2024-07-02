package com.markettwits.core_cloud.provider

import kotlinx.serialization.json.Json

class JsonProviderBase : JsonProvider {
    override fun provide(): Json =
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            useAlternativeNames = false
        }
}