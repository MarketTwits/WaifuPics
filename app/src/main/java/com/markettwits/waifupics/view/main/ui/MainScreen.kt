package com.markettwits.waifupics.view.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
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
    val state = remember {
        mutableStateOf<RandomImageUiState>(RandomImageUiState.Initial)
    }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.state.observe(LocalLifecycleOwner.current){
            state.value = it
        }
        state.value.Handle()
    }
}
