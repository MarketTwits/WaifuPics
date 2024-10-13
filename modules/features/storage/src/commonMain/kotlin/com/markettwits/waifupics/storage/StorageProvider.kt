package com.markettwits.waifupics.storage;



/**
 * Provides platform-specific paths for temporary and root directories.
 */
interface StorageProvider {
    /**
     * Returns the path to the temporary directory specific to the platform.
     *
     * @return A string representing the path to the temporary directory.
     */
    val tempPath : String
    /**
     * Returns the path to the root directory specific to the platform.
     *
     * @return A string representing the path to the root directory.
     * If the path cannot be determined, it returns the home directory path.
     */
    val rootPath : String
}
/**
 * Platform-specific implementation of StorageProvider.
 *
 * This value should be used in Android applications only within the Context provided by the Koin scope.
 *
 * @property PlatformStorageProvider An instance of StorageProvider that provides paths specific to the platform.
 */
expect val PlatformStorageProvider : StorageProvider

