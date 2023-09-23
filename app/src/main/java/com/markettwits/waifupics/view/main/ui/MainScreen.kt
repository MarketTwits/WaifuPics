package com.markettwits.waifupics.view.main.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel

@Composable
fun MainScreen(
    paddingValues: PaddingValues = PaddingValues(),
) {
    val viewModel: ImageViewModel = ApplicationViewModel()
    val state = viewModel.state().collectAsState()
    RandomImageColumn(paddingValues = paddingValues) {
        state.value.Handle()
    }
}