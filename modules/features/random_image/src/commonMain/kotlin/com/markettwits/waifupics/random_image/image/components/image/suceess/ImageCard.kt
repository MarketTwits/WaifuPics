package com.markettwits.waifupics.random_image.presentation.random_image.components.image.suceess

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.markettwits.cloud_datasource.presentation.random_image.components.image.ImageState
import com.markettwits.cloud_datasource.presentation.random_image.components.image.loading.ImageLoading
import com.markettwits.cloud_datasource.presentation.random_image.viewmodel.ImageViewModel
import com.markettwits.core_ui.theme.Shapes
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.waifupics.random_image.presentation.random_image.components.image.full_screen.FullImageScreen

@Composable
fun ImageCard(imageUrl: String, id: Int) {

    val viewModel: ImageViewModel = ApplicationViewModel()
    var isDialogOpen by remember { mutableStateOf(false) }
    val modifier = Modifier
        .sizeIn(minWidth = 200.dp, minHeight = 300.dp, maxHeight = 1080.dp, maxWidth = 1920.dp)
        .padding(20.dp)
        .clip(Shapes.medium)
        .clickable { isDialogOpen = true }

    SubcomposeAsyncImage(
        model = imageUrl,
        contentScale = ContentScale.Fit,
        contentDescription = "",
        loading = {
            ImageLoading()
            viewModel.obtainImageState(ImageState.Loading)
        }, success = {
            SubcomposeAsyncImageContent(modifier = modifier)
            viewModel.currentImage(imageUrl, id, it.result.image.width, it.result.image.height)
        },
        error = {
            val message = it.result.throwable.toString()
            viewModel.obtainImageState(ImageState.Error(message))
        }
    )
    if (isDialogOpen) {
        FullImageScreen(
            imageUrl = imageUrl,
            onDismiss = {
                isDialogOpen = false
            }
        )
    }

}





