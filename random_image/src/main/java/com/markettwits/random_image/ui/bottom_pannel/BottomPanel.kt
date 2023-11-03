package com.markettwits.random_image.ui.bottom_pannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.core_ui.R
import com.markettwits.random_image.ui.ImageViewModel
import com.markettwits.random_image.ui.bottom_pannel.panel.HeartButton
import com.markettwits.waifupics.base.BasePanelItem
import com.markettwits.waifupics.base.RefreshPanelItem


@Composable
fun BottomPanel(
    bottomPanelUiState: BottomPanelUiState,
    imageUrl: String = "",
    ageRating: String = ""
) {
    val viewModel: ImageViewModel.Base = ApplicationViewModel()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.ic_bookmark,
            isLoading = bottomPanelUiState.baseBottomEnabled()
        ) {}
        HeartButton(
            modifier = Modifier.weight(1f),
            loadingState = bottomPanelUiState,
            imageUrl = imageUrl,
            viewModel = viewModel
        )
        RefreshPanelItem(modifier = Modifier.weight(2f), isLoading = bottomPanelUiState.refresh()) {
            viewModel.fetchRandomImage()
        }
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.ic_share,
            isLoading = bottomPanelUiState.baseBottomEnabled()
        ) {
            viewModel.shareImage(imageUrl)
        }
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.ic_flag,
            isLoading = bottomPanelUiState.baseBottomEnabled()
        ) {}
    }
}
