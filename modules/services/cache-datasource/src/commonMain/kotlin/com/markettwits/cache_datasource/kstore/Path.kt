package com.markettwits.cache_datasource.kstore

/**
 * The directory for saving files in the device's cache
 *
 * ```
 *  InStorageCacheDirectory.path = cacheDir.path
 * ```
 */
object InStorageCacheDirectory {
    var path: String = ""
}

/**
 * The directory for saving files in the device's file storage
 *
 * ```
 *  InStorageFileDirectory.path = filesDir.path
 * ```
 */
object InStorageFileDirectory {
    var path = ""
}