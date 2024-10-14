package com.markettwits.waifupics.paths

import java.io.File

actual fun dataDirectory(appId: String): String {
    val fileDirectory = File(System.getProperty("user.home"), "Documents/$appId")
    fileDirectory.mkdirs()
    return fileDirectory.absolutePath
}

actual fun cacheDirectory(appId: String): String {
    val cacheDirectory = File(System.getProperty("java.io.tmpdir"), appId)
    cacheDirectory.mkdirs()
    return cacheDirectory.absolutePath
}