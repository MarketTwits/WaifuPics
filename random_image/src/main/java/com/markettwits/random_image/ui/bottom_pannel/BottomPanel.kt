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
import com.markettwits.waifupics.base.BasePanelItem
import com.markettwits.waifupics.base.RefreshPanelItem
import com.markettwits.waifupics.view.main.ui.bottom_pannel.BottomPanelUiState


@Composable
fun BottomPanel(bottomPanelUiState: BottomPanelUiState, imageUrl : String = "", ageRating : String ="") {
    val viewModel : ImageViewModel = ApplicationViewModel()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.bookmark_icon,
            isLoading = bottomPanelUiState.baseBottomEnabled()
        ) {}
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.heart_icon,
            isLoading = bottomPanelUiState.baseBottomEnabled()
        ) {
            viewModel.addToFavorite(imageUrl, ageRating)
        }
        RefreshPanelItem(modifier = Modifier.weight(2f), isLoading = bottomPanelUiState.refresh()) {
            viewModel.fetchRandomImage()
        }
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.share_icon,
            isLoading = bottomPanelUiState.baseBottomEnabled()
        ) {}
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = R.drawable.flag_icon,
            isLoading = bottomPanelUiState.baseBottomEnabled()
        ) {}
    }
}
