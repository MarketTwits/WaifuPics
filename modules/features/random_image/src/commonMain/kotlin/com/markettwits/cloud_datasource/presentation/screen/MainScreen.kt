package com.markettwits.cloud_datasource.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.markettwits.core_ui.di.ApplicationViewModel

@Composable
fun MainScreen() {
    val viewModel: ImageViewModel.Base = ApplicationViewModel()

    val state = viewModel.state().collectAsState()

    RandomImageColumn() {
        state.value.Handle()
    }
}