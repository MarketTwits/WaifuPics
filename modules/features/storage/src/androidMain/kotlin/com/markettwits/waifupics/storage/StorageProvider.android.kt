package com.markettwits.waifupics.storage

import android.content.Context
import org.koin.java.KoinJavaComponent

class AndroidStorageProvider(context: Context) : StorageProvider {

    override val tempPath: String = context.cacheDir.path
    override val rootPath: String = context.filesDir.path

}

actual val PlatformStorageProvider: StorageProvider = AndroidStorageProvider(getContext())

private fun getContext(): Context {
    val context: Context by KoinJavaComponent.inject(Context::class.java)
    return context
}
