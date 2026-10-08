package com.markettwits.waifupics.random.components.bottom_pannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.waifupics.random.components.bottom_pannel.buttons.BasePanelItem
import com.markettwits.waifupics.random.components.bottom_pannel.buttons.HeartButton
import com.markettwits.waifupics.random.components.bottom_pannel.buttons.RefreshPanelItem
import com.markettwits.waifupics.random.components.bottom_pannel.buttons.ReportedButton
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.features.random.image.generated.resources.Res
import waifupics.modules.features.random.image.generated.resources.ic_filter
import waifupics.modules.features.random.image.generated.resources.ic_share


@Composable
internal fun BottomPanel(
    modifier: Modifier = Modifier,
    isEnabled : Boolean,
    isRefresh : Boolean,
    imageId : Int,
    onClickAddToFavorite : () -> Unit,
    onClickFetchRandomImage : () -> Unit,
    onClickFilter : () -> Unit,
    onClickShareImage : () -> Unit,
    isFavorite: Boolean,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        HeartButton(
            modifier = Modifier.weight(0.9f),
            enabled = isEnabled,
            checked = isFavorite,
            onClickAddToFavorite = onClickAddToFavorite,
        )
        BasePanelItem(
            modifier = Modifier.weight(0.9f),
            image = painterResource(Res.drawable.ic_filter),
            onClick = onClickFilter,
            enabled = isEnabled
        )
        RefreshPanelItem(
            modifier = Modifier.weight(2.5f),
            isLoading = isRefresh,
            onClick = onClickFetchRandomImage
        )
        BasePanelItem(
            modifier = Modifier.weight(0.9f),
            image = painterResource(Res.drawable.ic_share),
            enabled = isEnabled,
            onClick = onClickShareImage
        )
        ReportedButton(
            modifier = Modifier.weight(0.9f),
            enabled = isEnabled,
            imageId = imageId
        )
    }
}
