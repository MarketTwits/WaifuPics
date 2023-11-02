package com.markettwits.random_image.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.markettwits.core_ui.ApplicationViewModel

@Composable
fun MainScreen(
    paddingValues: PaddingValues = PaddingValues(),
    firstRun : Boolean,
) {
    val viewModel: ImageViewModel = ApplicationViewModel()
    val state = viewModel.state().collectAsState()
    RandomImageColumn(paddingValues = paddingValues) {
        state.value.Handle(firstRun)
    }
}