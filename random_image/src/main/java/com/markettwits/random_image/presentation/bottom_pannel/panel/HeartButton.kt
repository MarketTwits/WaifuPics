package com.markettwits.random_image.presentation.bottom_pannel.panel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.R
import com.markettwits.core_ui.base.BasePanelItem
import com.markettwits.random_image.presentation.ImageViewModel

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
        Log.d("mt05", checked.toString())
        checked = !checked
        viewModel.addToFavorite()
    }
}