package com.markettwits.waifupics.paths

import kotlinx.io.files.Path

expect fun dataDirectory(appId: String) : String

expect fun cacheDirectory(appId: String) : String