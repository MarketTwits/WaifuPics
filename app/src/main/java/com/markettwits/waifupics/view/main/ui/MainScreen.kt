package com.markettwits.waifupics.view.main.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.markettwits.waifupics.core.WaifuPicsApp
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
) {
    val viewModel = (LocalContext.current.applicationContext as WaifuPicsApp).viewModel(
        checkNotNull(LocalViewModelStoreOwner.current), ImageViewModel::class.java
    )
    val state = viewModel.fetch().collectAsState()
    RandomImageColumn(paddingValues = paddingValues) {
        viewModel.updateState {
            state.value
        }
        state.value.Handle()
    }
}