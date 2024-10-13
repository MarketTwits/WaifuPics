package com.markettwits.cloud_datasource.presentation.random_image.components.bottom_pannel.buttons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.ImageViewModel
import com.markettwits.waifupics.cloud_datasource.presentation.random_image.components.bottom_pannel.buttons.BasePanelItem
import org.jetbrains.compose.resources.painterResource
import waifupics.modules.features.random_image.generated.resources.Res
import waifupics.modules.features.random_image.generated.resources.ic_heart
import waifupics.modules.features.random_image.generated.resources.ic_heart_solid

@Composable
fun HeartButton(
    modifier: Modifier,
    enabled: Boolean,
    viewModel: ImageViewModel,
) {
    var checked by rememberSaveable{
        mutableStateOf(false)
    }
    val image = if (checked) painterResource(Res.drawable.ic_heart_solid)  else painterResource(Res.drawable.ic_heart)
    BasePanelItem(
        modifier = modifier,
        image = image,
        enabled = enabled
    ) {
        checked = !checked
        viewModel.onClickAddToFavorite()
    }
}