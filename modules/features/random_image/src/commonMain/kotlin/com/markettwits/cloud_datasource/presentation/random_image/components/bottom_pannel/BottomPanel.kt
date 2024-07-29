package com.markettwits.cloud_datasource.presentation.random_image.components.bottom_pannel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.cloud_datasource.presentation.random_image.components.bottom_pannel.buttons.BasePanelItem
import com.markettwits.cloud_datasource.presentation.random_image.components.bottom_pannel.buttons.HeartButton
import com.markettwits.cloud_datasource.presentation.random_image.components.bottom_pannel.buttons.RefreshPanelItem
import com.markettwits.cloud_datasource.presentation.random_image.components.bottom_pannel.buttons.ReportedButton
import com.markettwits.cloud_datasource.presentation.random_image.components.image.ImageState
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.ImageViewModel
import com.markettwits.core_ui.di.ApplicationViewModel
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.features.random_image.generated.resources.Res
import waifupics.modules.features.random_image.generated.resources.ic_bookmark
import waifupics.modules.features.random_image.generated.resources.ic_share


@Composable
fun BottomPanel() {
    val viewModel: ImageViewModel = ApplicationViewModel()
    val panelState = viewModel.loadedImageState().collectAsState()
    val enabled = panelState.value is ImageState.Success
    val refresh = panelState.value is ImageState.Loading
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = painterResource(Res.drawable.ic_bookmark),
            enabled = enabled
        ) {}
        HeartButton(
            modifier = Modifier.weight(1f),
            enabled = enabled,
            viewModel = viewModel
        )
        RefreshPanelItem(modifier = Modifier.weight(2.5f), isLoading = refresh) {
            viewModel.fetchRandomImage()
        }
        BasePanelItem(
            modifier = Modifier.weight(1f),
            image = painterResource(Res.drawable.ic_share),
            enabled = enabled
        ) {
            viewModel.onClickShareImage()
        }
        ReportedButton(
            modifier = Modifier.weight(1f),
            enabled = enabled,
        )
    }
}
