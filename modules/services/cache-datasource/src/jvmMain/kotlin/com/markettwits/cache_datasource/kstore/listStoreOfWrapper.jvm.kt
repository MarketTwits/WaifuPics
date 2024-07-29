package com.markettwits.cache_datasource.kstore

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.extensions.listStoreOf
import io.github.xxfast.kstore.file.storeOf
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

actual inline fun <reified T : @Serializable Any> storeOfWrapper(
    path: String,
    fileName: String,
    default: T?,
    enableCache: Boolean,
    json: Json
): KStore<T> {
    val fullPath = "$path/$fileName".toPath()
    return storeOf(fullPath, default, enableCache, json)
}

actual inline fun <reified T : @Serializable Any> listStoreOfWrapper(
    path: String,
    fileName: String,
    default: List<T>,
    enableCache: Boolean,
    json: Json
): KStore<List<T>> {
    val fullPath = "$path/$fileName".toPath()
    return listStoreOf(fullPath, default, enableCache, json)
}