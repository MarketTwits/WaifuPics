package com.markettwits.core


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.markettwits.core.di.ProvideViewModel
import com.markettwits.core.di.ViewModelsFactory
import kotlin.reflect.KClass

class DefaultWaifuPicsApp : ProvideViewModel {

    private var viewModelFactory: ViewModelsFactory =
        ViewModelsFactory(MakeDependencies.Base().dependencies())

    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: KClass<T>): T =
        ViewModelProvider.create(owner = owner, factory = viewModelFactory)[className]
}
