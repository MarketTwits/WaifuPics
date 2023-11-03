package com.markettwits.random_image.ui.bottom_pannel.panel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.R
import com.markettwits.random_image.ui.ImageViewModel
import com.markettwits.random_image.ui.bottom_pannel.BottomPanelUiState
import com.markettwits.waifupics.base.BasePanelItem

@Composable
fun HeartButton(
    modifier: Modifier,
    loadingState: BottomPanelUiState,
    imageUrl: String,
    viewModel: ImageViewModel
) {
    var checked by rememberSaveable{
        mutableStateOf(false)
    }
    BasePanelItem(
        modifier = modifier,
        image = if (checked) R.drawable.ic_heart_solid else R.drawable.ic_heart,
        isLoading = loadingState.baseBottomEnabled()
    ) {
        checked = !checked
        viewModel.addToFavorite(imageUrl, true)
    }
}