package com.markettwits.waifupics.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.markettwits.waifupics.core.sl.DependencyContainer
import com.markettwits.waifupics.core.sl.ProvideViewModel
import com.markettwits.waifupics.core.sl.ViewModelsFactory

class WaifuPicsApp : Application(), ProvideViewModel {
    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T {
        return ViewModelProvider(owner, viewModelFactory)[className]
    }
    private lateinit var viewModelFactory: ViewModelsFactory
    private lateinit var core: Core
    override fun onCreate() {
        super.onCreate()
        core = Core(this)
        viewModelFactory = ViewModelsFactory(
            DependencyContainer.Base(core)
        )
    }
}