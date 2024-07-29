package com.markettwits.cache_datasource.kstore

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

actual inline fun <reified T : @Serializable Any> storeOfWrapper(
    path: String,
    fileName: String,
    default: T?,
    enableCache: Boolean,
    json: Json
): KStore<T> {
    return storeOf(fileName, default)
}


actual inline fun <reified T : @Serializable Any> listStoreOfWrapper(
    path: String,
    fileName: String,
    default: List<T>,
    enableCache: Boolean,
    json: Json
): KStore<List<T>> {
    return storeOf(fileName, default)
}