package com.markettwits.waifupics.paths

import me.sujanpoudel.utils.contextProvider.applicationContext
import java.io.File

actual fun dataDirectory(appId: String): String {
    val fileDirectory = File(applicationContext.filesDir, appId)
    fileDirectory.mkdirs()
    return fileDirectory.absolutePath
}

actual fun cacheDirectory(appId: String): String {
    val cacheDirectory = File(applicationContext.cacheDir, appId)
    cacheDirectory.mkdirs()
    return cacheDirectory.absolutePath
}