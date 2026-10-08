package com.markettwits.waifupics.gallery.item.components.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.markettwits.core_ui.components.design.WaifuActionTile

@Composable
internal fun BottomBarColumn(imageVector: ImageVector, title: String, overlay: Boolean = false,
    onItemClick: () -> Unit) {
    WaifuActionTile(label = title, icon = imageVector, onClick = onItemClick, overlay = overlay)
}
