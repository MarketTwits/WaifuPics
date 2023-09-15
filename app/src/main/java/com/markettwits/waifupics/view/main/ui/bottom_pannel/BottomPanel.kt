package com.markettwits.waifupics.view.main.ui.bottom_pannel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.markettwits.waifupics.base.BasePanelItem
import com.markettwits.waifupics.base.RefreshPanelItem
import com.markettwits.waifupics.core.WaifuPicsApp
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel

@Preview
@Composable
fun BottomPanel() {
    val viewModel = (LocalContext.current.applicationContext as WaifuPicsApp).viewModel(
        checkNotNull(LocalViewModelStoreOwner.current), ImageViewModel::class.java
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasePanelItem(modifier = Modifier.weight(1f), image = Icons.Default.BookmarkBorder) {}
        BasePanelItem(modifier = Modifier.weight(1f), image = Icons.Outlined.HeartBroken) {}
        RefreshPanelItem(modifier = Modifier.weight(2.5f)) {
            viewModel.fetchRandomImage()
        }
        BasePanelItem(modifier = Modifier.weight(1f), image = Icons.Outlined.Share) {}
        BasePanelItem(modifier = Modifier.weight(1f), image = Icons.Outlined.Flag) {}
    }
}
