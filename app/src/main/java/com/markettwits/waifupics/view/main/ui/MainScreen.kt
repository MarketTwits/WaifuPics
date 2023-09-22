package com.markettwits.waifupics.view.main.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.markettwits.core_ui.ActivityViewModel
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
) {
    val viewModel: ImageViewModel = ActivityViewModel()
    val state = viewModel.state().collectAsState()
    RandomImageColumn(paddingValues = paddingValues) {
        state.value.Handle()
    }
}