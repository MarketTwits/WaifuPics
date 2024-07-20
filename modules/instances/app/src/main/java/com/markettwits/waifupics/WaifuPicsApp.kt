package com.markettwits.waifupics

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

import com.markettwits.core.di.ProvideViewModel
import com.markettwits.core.di.ViewModelsFactory
import com.markettwits.core.initKoin
import com.markettwits.core.initKoinApp

import com.markettwits.data.cache.InStorageFileDirectory
import org.koin.android.ext.koin.androidContext
import kotlin.reflect.KClass

class WaifuPicsApp : Application() {

   // private lateinit var viewModelFactory: ViewModelsFactory

    override fun onCreate() {
        super.onCreate()
        InStorageFileDirectory.path = filesDir.path
        initKoinApp(appDeclaration = {
            androidContext(applicationContext)
        })

//        val make = MakeDependencies.Base()
//        viewModelFactory = ViewModelsFactory(make.dependencies())
    }

//    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: KClass<T>): T =
//         ViewModelProvider(owner, viewModelFactory)[className]


}
