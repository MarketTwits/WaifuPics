package com.markettwits.cloud_datasource.presentation.random_image.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.ImageViewModel
import com.markettwits.core_ui.di.ApplicationViewModel

@Composable
fun RandomImageScreen() {
    val viewModel: ImageViewModel = ApplicationViewModel()

    val state = viewModel.state().collectAsState()

    RandomImageColumn {
        state.value.Handle()
    }
}