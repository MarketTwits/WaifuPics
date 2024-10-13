package com.markettwits.waifupics.storage

import java.io.File
import kotlin.io.path.pathString

class JVMStorageProvider : StorageProvider {
    override val tempPath: String = File(System.getProperty("java.io.tmpdir")).toPath().pathString
    override val rootPath: String = File(System.getProperty("user.home")).toPath().pathString
}

actual val PlatformStorageProvider : StorageProvider = JVMStorageProvider()