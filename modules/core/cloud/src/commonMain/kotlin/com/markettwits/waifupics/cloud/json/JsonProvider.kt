package com.markettwits.waifupics.cloud.json

import kotlinx.serialization.json.Json

interface JsonProvider {
    fun provide(): Json
}