package com.markettwits.waifupics.random_image.image.components.bottom_pannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.waifupics.random_image.image.components.bottom_pannel.buttons.BasePanelItem
import com.markettwits.waifupics.random_image.image.components.bottom_pannel.buttons.HeartButton
import com.markettwits.waifupics.random_image.image.components.bottom_pannel.buttons.RefreshPanelItem
import com.markettwits.waifupics.random_image.image.components.bottom_pannel.buttons.ReportedButton
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.features.random_image.generated.resources.Res
import waifupics.modules.features.random_image.generated.resources.ic_bookmark
import waifupics.modules.features.random_image.generated.resources.ic_share


@Composable
fun BottomPanel(
    modifier: Modifier = Modifier,
    isEnabled : Boolean,
    isRefresh : Boolean,
    onClickAddToFavorite : () -> Unit,
    onClickFetchRandomImage : () -> Unit,
    onClickShareImage : () -> Unit,
) {
//    val viewModel: ImageViewModel = ApplicationViewModel()
//    val panelState = viewModel.loadedImageState().collectAsState()
//    val enabled = panelState.value is ImageState.Success
//    val refresh = panelState.value is ImageState.Loading
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
        )
    }
}
