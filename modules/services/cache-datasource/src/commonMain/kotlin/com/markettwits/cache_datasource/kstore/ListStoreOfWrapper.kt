package com.markettwits.cache_datasource.kstore

import io.github.xxfast.kstore.KStore
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json



internal expect inline fun <reified T : @Serializable Any> storeOfWrapper(
    path: String,
    fileName: String,
    default: T? = null,
    enableCache: Boolean = true,
    json: Json = Json { ignoreUnknownKeys = true; encodeDefaults = true },
): KStore<T>

internal expect inline fun <reified T : @Serializable Any> listStoreOfWrapper(
    path: String,
    fileName: String,
    default: List<T> = emptyList(),
    enableCache: Boolean = true,
    json: Json = Json { ignoreUnknownKeys = true; encodeDefaults = true },
): KStore<List<T>>


