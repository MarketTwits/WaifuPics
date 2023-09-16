package com.markettwits.waifupics.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.markettwits.core.sl.ProvideViewModel
import com.markettwits.core.sl.ViewModelsFactory

class WaifuPicsApp : Application(), ProvideViewModel {

    private lateinit var viewModelFactory: ViewModelsFactory
    override fun onCreate() {
        super.onCreate()
        val make = MakeDependencies.Base(this)
        viewModelFactory = ViewModelsFactory(make.dependencies())
    }
    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T {
        return ViewModelProvider(owner, viewModelFactory)[className]
    }
}