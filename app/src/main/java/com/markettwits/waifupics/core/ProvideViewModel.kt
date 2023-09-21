package com.markettwits.waifupics.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
inline fun <reified T : ViewModel>ProvideViewModel(): T {
    return (LocalContext.current.applicationContext as WaifuPicsApp).viewModel(
        LocalViewModelStoreOwner.current!!,
        T::class.java
    )
}