package com.markettwits.random_image.presentation.features.bottom_pannel.panel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.R
import com.markettwits.core_ui.components.BasePanelItem
import com.markettwits.random_image.presentation.random_image_screen.ImageViewModel

@Composable
fun HeartButton(
    modifier: Modifier,
    enabled: Boolean,
    viewModel: ImageViewModel,
) {
    var checked by rememberSaveable{
        mutableStateOf(false)
    }
    BasePanelItem(
        modifier = modifier,
        image = if (checked) R.drawable.ic_heart_solid else R.drawable.ic_heart,
        enabled = enabled
    ) {
        checked = !checked
        viewModel.addToFavorite()
    }
}