package com.markettwits.core_ui

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.markettwits.core.sl.ProvideViewModel

abstract class MainComponentActivity :  ComponentActivity(), ProvideViewModel{
    override fun <T : ViewModel> viewModel(owner: ViewModelStoreOwner, className: Class<T>): T =
        (application as ProvideViewModel).viewModel(owner, className)
}