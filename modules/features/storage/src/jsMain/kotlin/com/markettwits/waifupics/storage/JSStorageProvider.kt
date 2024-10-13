package com.markettwits.waifupics.storage

class JSStorageProvider : StorageProvider {
    override val tempPath: String = ""
    override val rootPath: String = ""
}

actual val PlatformStorageProvider : StorageProvider = JSStorageProvider()