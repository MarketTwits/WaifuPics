package com.markettwits.waifupics.storage

class WASMStorageProvider : StorageProvider {
    override val tempPath: String = ""
    override val rootPath: String = ""
}

actual val PlatformStorageProvider : StorageProvider = WASMStorageProvider()