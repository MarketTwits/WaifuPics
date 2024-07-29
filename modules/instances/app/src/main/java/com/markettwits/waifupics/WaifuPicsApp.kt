package com.markettwits.waifupics

import android.app.Application
import com.markettwits.core.initKoinApp
import com.markettwits.cache_datasource.kstore.InStorageFileDirectory
import org.koin.android.ext.koin.androidContext

class WaifuPicsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        InStorageFileDirectory.path = filesDir.path
        initKoinApp(appDeclaration = {
            androidContext(applicationContext)
        })
    }

}
