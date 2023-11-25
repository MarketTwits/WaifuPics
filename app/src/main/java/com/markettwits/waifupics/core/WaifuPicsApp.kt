package com.markettwits.waifupics.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.markettwits.core.di.ProvideViewModel
import com.markettwits.core.di.ViewModelsFactory
import com.markettwits.core.wrappers.SaveAndRestore
import com.markettwits.core.wrappers.SaveAndRestoreState
import com.markettwits.core.wrappers.WrapBundle

class WaifuPicsApp : Application(), ProvideViewModel, SaveAndRestore {

    private lateinit var viewModelFactory: ViewModelsFactory

    private val saveAndRestoreState : SaveAndRestoreState = SaveAndRestoreState.Base()
    override fun onCreate() {
        super.onCreate()
        val make = MakeDependencies.Base(this, saveAndRestoreState)
        viewModelFactory = ViewModelsFactory(make.dependencies())

    }
    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T {
        return ViewModelProvider(owner, viewModelFactory)[className]
    }

    override fun save(bundle: WrapBundle) = saveAndRestoreState.save(bundle)

    override fun restore(bundle: WrapBundle) = saveAndRestoreState.restore(bundle)

    override fun init(firstRun: Boolean) = saveAndRestoreState.init(firstRun)
}