package com.markettwits.waifupics.random.components.bottom_pannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.waifupics.random.components.bottom_pannel.buttons.BasePanelItem
import com.markettwits.waifupics.random.components.bottom_pannel.buttons.HeartButton
import com.markettwits.waifupics.random.components.bottom_pannel.buttons.RefreshPanelItem
import com.markettwits.waifupics.random.components.bottom_pannel.buttons.ReportedButton
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.features.random.image.generated.resources.Res
import waifupics.modules.features.random.image.generated.resources.ic_bookmark
import waifupics.modules.features.random.image.generated.resources.ic_share


@Composable
internal fun BottomPanel(
    modifier: Modifier = Modifier,
    isEnabled : Boolean,
    isRefresh : Boolean,
    imageId : Int,
    onClickAddToFavorite : () -> Unit,
    onClickFetchRandomImage : () -> Unit,
    onClickShareImage : () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = painterResource(Res.drawable.ic_bookmark),
            enabled = isEnabled
        ) {}
        HeartButton(
            modifier = Modifier.weight(1f),
            enabled = isEnabled,
            onClickAddToFavorite = onClickAddToFavorite,
        )
        RefreshPanelItem(
            modifier = Modifier.weight(2.5f),
            isLoading = isRefresh,
            onClick = onClickFetchRandomImage
        )
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = painterResource(Res.drawable.ic_share),
            enabled = isEnabled,
            onClick = onClickShareImage
        )
        ReportedButton(
            modifier = Modifier.weight(1f),
            enabled = isEnabled,
            imageId = imageId
        )
    }
}
