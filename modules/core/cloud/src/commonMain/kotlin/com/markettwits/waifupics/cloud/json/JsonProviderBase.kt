package com.markettwits.waifupics.cloud.json

import com.markettwits.core_cloud.provider.JsonProvider
import kotlinx.serialization.json.Json

class JsonProviderBase : JsonProvider {
    override fun provide(): Json =
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            useAlternativeNames = false
        }
}