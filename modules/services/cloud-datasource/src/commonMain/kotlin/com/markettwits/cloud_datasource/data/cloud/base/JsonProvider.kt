package com.markettwits.core_cloud.provider

import kotlinx.serialization.json.Json

interface JsonProvider {
    fun provide(): Json
}