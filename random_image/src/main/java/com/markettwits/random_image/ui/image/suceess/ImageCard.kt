package com.markettwits.random_image.ui.image.suceess

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.core_ui.R
import com.markettwits.core_ui.image.LocalImageLoader
import com.markettwits.random_image.ui.ImageViewModel
import com.markettwits.random_image.ui.image.loading.ImageLoading
import com.markettwits.waifupics.view.main.ui.image.zoom.FullScreenImageDialog


@Composable
fun ImageCard(imageUrl: String) {
    var viewModel : ImageViewModel.Base = ApplicationViewModel()
    var isDialogOpen by remember{ mutableStateOf(false) }
    val modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .wrapContentHeight()
        .clickable { isDialogOpen = true }
        SubcomposeAsyncImage(
            model = LocalImageLoader.current.memorySingle(imageUrl),
            contentScale = ContentScale.FillWidth,
            contentDescription = stringResource(R.string.waifu_image)
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Success){
                viewModel.currentImage(state.result.drawable, imageUrl)
            }
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                ImageLoading()
            } else {
                SubcomposeAsyncImageContent(modifier = modifier)
            }
        }
        if (isDialogOpen) {
            FullScreenImageDialog(
                imageUrl = imageUrl,
                onClose = { isDialogOpen = false }
            )
        }
    }



