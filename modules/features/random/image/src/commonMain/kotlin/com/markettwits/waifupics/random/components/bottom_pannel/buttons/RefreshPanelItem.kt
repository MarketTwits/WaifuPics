package com.markettwits.waifupics.random.components.bottom_pannel.buttons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.components.design.WaifuActionButton
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.Res
import waifupics.modules.features.random.image.generated.resources.refresh

@Composable
fun RefreshPanelItem(isLoading: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    WaifuActionButton(stringResource(Res.string.refresh), onClick, modifier,
        icon = Icons.Default.Refresh, enabled = !isLoading)
}
