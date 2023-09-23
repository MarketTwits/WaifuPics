package com.markettwits.core_ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.markettwits.core.sl.ProvideViewModel

@Composable
inline fun <reified T : ViewModel> ApplicationViewModel(): T {
    return (LocalContext.current.applicationContext as ProvideViewModel).viewModel(
        checkNotNull(
            LocalViewModelStoreOwner.current
        ), T::class.java
    )
}