package com.markettwits.waifupics.view.main.bottom_pannel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.markettwits.waifupics.view.base.BasePanelItem
import com.markettwits.waifupics.view.base.RefreshPanelItem

@Preview
@Composable
fun BottomPanel() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasePanelItem(
            image = Icons.Default.BookmarkBorder
        ) {}
        BasePanelItem(image = Icons.Outlined.HeartBroken) {}
        RefreshPanelItem() {}
        BasePanelItem(image = Icons.Outlined.Share) {}
        BasePanelItem(image = Icons.Outlined.Flag) {}
    }
}
