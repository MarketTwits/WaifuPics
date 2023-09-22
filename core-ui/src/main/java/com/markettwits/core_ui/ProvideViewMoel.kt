package com.markettwits.core_ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
inline fun <reified T : ViewModel> ActivityViewModel(): T {
    return (LocalContext.current as MainComponentActivity).viewModel(
        checkNotNull(
            LocalViewModelStoreOwner.current
        ), T::class.java
    )
}