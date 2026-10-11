package com.markettwits.waifupics.cache.kstore

import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.extensions.listStoreOf
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

actual inline fun <reified T : @Serializable Any> storeOfWrapper(
    path: String,
    fileName: String,
    default: T?,
    enableCache: Boolean,
    json: Json
): KStore<T> {
    val fullPath = Path("$path/$fileName")
    return storeOf(fullPath, default, enableCache, json)
}

actual inline fun <reified T : @Serializable Any> listStoreOfWrapper(
    path: String,
    fileName: String,
    default: List<T>,
    enableCache: Boolean,
    json: Json
): KStore<List<T>> {
    val fullPath = Path("$path/$fileName")
    return listStoreOf(fullPath, default, enableCache, json)
}