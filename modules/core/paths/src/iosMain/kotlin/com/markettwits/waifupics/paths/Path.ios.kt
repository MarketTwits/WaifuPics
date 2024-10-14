package com.markettwits.waifupics.paths

import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun dataDirectory(appId: String): String =
    NSSearchPathForDirectoriesInDomains(NSApplicationSupportDirectory, NSUserDomainMask, true)
        .firstOrNull()?.toString() ?: error("Unable to get 'NSApplicationSupportDirectory'")

actual fun cacheDirectory(appId: String): String =
    NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, true)
        .firstOrNull()?.toString() ?: error("Unable to get 'NSCachesDirectory'")

