package com.markettwits.core_ui.di

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

@Composable
inline fun <reified T : ViewModel> ApplicationViewModel(): T =
    LocalViewModelProvider.current.viewModel(
        checkNotNull(
            LocalViewModelStoreOwner.current
        ), T::class
    )