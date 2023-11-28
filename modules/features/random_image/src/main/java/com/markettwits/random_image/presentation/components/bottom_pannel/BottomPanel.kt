package com.markettwits.random_image.presentation.components.bottom_pannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.random_image.R
import com.markettwits.random_image.presentation.components.bottom_pannel.button.BasePanelItem
import com.markettwits.random_image.presentation.components.bottom_pannel.button.RefreshPanelItem
import com.markettwits.random_image.presentation.components.bottom_pannel.button.HeartButton
import com.markettwits.random_image.presentation.components.bottom_pannel.button.ReportedButton
import com.markettwits.random_image.presentation.random_image_screen.ImageViewModel
import com.markettwits.random_image.presentation.random_image_screen.LoadedImage


@Composable
fun BottomPanel() {
    val viewModel: ImageViewModel.Base = ApplicationViewModel()
    val panelState = viewModel.loadedImageState().collectAsState()
    val enabled = panelState.value !is LoadedImage.Loading
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.ic_bookmark,
            enabled = enabled
        ) {}
        HeartButton(
            modifier = Modifier.weight(1f),
            enabled = enabled,
            viewModel = viewModel
        )
        RefreshPanelItem(modifier = Modifier.weight(2f), isLoading = enabled) {
            viewModel.fetchRandomImage()
        }
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.ic_share,
            enabled = enabled
        ) {
            viewModel.shareImage()
        }
        ReportedButton(
            modifier = Modifier.weight(1f),
            enabled = enabled,
        )

    }
}
