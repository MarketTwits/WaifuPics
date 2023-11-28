package com.markettwits.core_ui.di

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.markettwits.core.di.ProvideViewModel

@Composable
inline fun <reified T : ViewModel> ApplicationViewModel(): T =
    (LocalContext.current.applicationContext as ProvideViewModel).viewModel(
        checkNotNull(
            LocalViewModelStoreOwner.current
        ), T::class.java
    )