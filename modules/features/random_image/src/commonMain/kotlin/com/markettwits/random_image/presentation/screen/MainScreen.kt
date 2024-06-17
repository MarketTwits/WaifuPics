package com.markettwits.random_image.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.markettwits.core_ui.di.ApplicationViewModel

@Composable
fun MainScreen(
    paddingValues: PaddingValues = PaddingValues(),
) {
    val viewModel: ImageViewModel.Base = ApplicationViewModel()

    val state = viewModel.state().collectAsState()

    RandomImageColumn(paddingValues = paddingValues) {
        state.value.Handle()
    }
}