package com.markettwits.waifupics.paths

actual fun dataDirectory(appId: String): String {
    return  "data/$appId"
}

actual fun cacheDirectory(appId: String): String {
    return "cache/$appId"
}