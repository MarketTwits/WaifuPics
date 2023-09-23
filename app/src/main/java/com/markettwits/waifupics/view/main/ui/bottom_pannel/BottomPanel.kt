package com.markettwits.waifupics.view.main.ui.bottom_pannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.markettwits.core_ui.R
import com.markettwits.waifupics.base.BasePanelItem
import com.markettwits.waifupics.base.RefreshPanelItem
import com.markettwits.waifupics.core.WaifuPicsApp
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel


@Composable
fun BottomPanel(bottomPanelUiState: BottomPanelUiState) {
    val viewModel = (LocalContext.current.applicationContext as WaifuPicsApp).viewModel(
        checkNotNull(LocalViewModelStoreOwner.current), ImageViewModel::class.java
    )
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
        ) {}
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
