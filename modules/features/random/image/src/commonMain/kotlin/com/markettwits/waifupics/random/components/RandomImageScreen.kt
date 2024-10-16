package com.markettwits.waifupics.random.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.random.viewmodel.ImageViewModel

@Composable
fun RandomImageScreen(viewModel: ImageViewModel = ApplicationViewModel()) {

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RandomImageScreenComponent(viewModel).Render()
    }
}