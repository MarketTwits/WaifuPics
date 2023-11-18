package com.markettwits.random_image.ui.bottom_pannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.core_ui.R
import com.markettwits.core_ui.base.BasePanelItem
import com.markettwits.core_ui.base.RefreshPanelItem
import com.markettwits.random_image.ui.ImageViewModel
import com.markettwits.random_image.ui.LoadedImage
import com.markettwits.random_image.ui.bottom_pannel.panel.HeartButton
import com.markettwits.random_image.ui.bottom_pannel.panel.ReportedButton


@Composable
fun BottomPanel(
    imageUrl: String = "",
) {
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
            // enabled = bottomPanelUiState.baseBottomEnabled()
            enabled = enabled
        ) {}
        HeartButton(
            modifier = Modifier.weight(1f),
            // enabled = bottomPanelUiState.baseBottomEnabled(),
            enabled = enabled,
            viewModel = viewModel
        )
        RefreshPanelItem(modifier = Modifier.weight(2f), isLoading = enabled) {
            viewModel.fetchRandomImage()
        }
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.ic_share,
            //  enabled = bottomPanelUiState.baseBottomEnabled()
            enabled = enabled
        ) {
            viewModel.shareImage()
        }
        ReportedButton(
            modifier = Modifier.weight(1f),
            // enabled = bottomPanelUiState.baseBottomEnabled(),
            enabled = enabled,
            viewModel = viewModel
        )
//        BasePanelItem(
//            modifier = Modifier.weight(1f),
//            image = R.drawable.ic_flag,
//            enabled = bottomPanelUiState.baseBottomEnabled()
//        ) {
//            viewModel.changeReportedDialogState()
//        }
    }
}
