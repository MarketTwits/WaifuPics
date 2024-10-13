package com.markettwits.waifupics.storage

import platform.Foundation.NSFileManager

class IosStorageProvider : StorageProvider {

     override val tempPath: String
        get() = NSFileManager.defaultManager.CachesDirectory?.relativePath.toString()
    override val rootPath: String
        get() = NSFileManager.defaultManager.CachesDirectory?.relativePath.toString()

}

actual val PlatformStorageProvider : StorageProvider = IosStorageProvider()
