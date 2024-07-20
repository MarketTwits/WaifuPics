package com.markettwits.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

interface ProvideViewModel {

    fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: KClass<T>): T

}