package com.markettwits.waifupics.random.components.bottom_pannel

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.waifupics.random.components.image_state.ImageState

@Composable
internal fun ConfigureBottomPanel(
    modifier: Modifier = Modifier,
    imageState: ImageState,
    imageId : Int,
    onClickAddToFavorite: () -> Unit,
    onClickFetchRandomImage: () -> Unit,
    onClickShareImage: () -> Unit,
) {

    val enabled = imageState is ImageState.Success
    val refresh = imageState is ImageState.Loading

    BottomPanel(
        modifier = modifier,
        isEnabled = enabled,
        isRefresh = refresh,
        imageId = imageId,
        onClickAddToFavorite = onClickAddToFavorite,
        onClickShareImage = onClickShareImage,
        onClickFetchRandomImage = onClickFetchRandomImage
    )
}