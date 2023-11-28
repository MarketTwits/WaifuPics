package com.markettwits.random_image.presentation.components.image.suceess

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.markettwits.core_ui.R
import com.markettwits.core_ui.components.Shapes
import com.markettwits.core_ui.image.LocalImageLoader
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.random_image.presentation.components.image.loading.ImageLoading
import com.markettwits.random_image.presentation.components.image.zoom.FullScreenImageDialog
import com.markettwits.random_image.presentation.random_image_screen.ImageViewModel


@Composable
fun ImageCard(imageUrl: String, id: Int) {
    val viewModel: ImageViewModel.Base = ApplicationViewModel()
    var isDialogOpen by remember { mutableStateOf(false) }
    val modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()
        .clip(Shapes.medium)
        .wrapContentHeight()
        .clickable { isDialogOpen = true }
    SubcomposeAsyncImage(
        model = LocalImageLoader.current.memorySingle(imageUrl),
        contentScale = ContentScale.FillWidth,
        contentDescription = stringResource(R.string.waifu_image),
        loading = {
            ImageLoading()
            viewModel.imageLoading()
        }, success = {
            SubcomposeAsyncImageContent(modifier = modifier)
            viewModel.currentImage(it.result.drawable, imageUrl, id)
        }
    )
    if (isDialogOpen) {
        FullScreenImageDialog(
            imageUrl = imageUrl,
            onClose = { isDialogOpen = false }
        )
    }
}


