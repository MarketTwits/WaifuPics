package com.markettwits.waifupics

import android.app.Application
import com.markettwits.waifupics.root.core.initKoinApp
import org.koin.android.ext.koin.androidContext

class WaifuPicsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoinApp(appDeclaration = {
            androidContext(applicationContext)
        })
    }

}
