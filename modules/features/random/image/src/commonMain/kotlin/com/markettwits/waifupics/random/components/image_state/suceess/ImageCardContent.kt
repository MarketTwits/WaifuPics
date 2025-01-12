package com.markettwits.waifupics.random.components.image_state.suceess

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
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.components.image_state.full_screen.FullImageScreen
import com.markettwits.waifupics.random.components.image_state.loading.ImageLoading

@Composable
internal fun ImageCardContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    id: Int,
    onChangeImageState: (ImageState) -> Unit
) {

    var isDialogOpen by remember { mutableStateOf(false) }


    val innerModifier = modifier
        .sizeIn(minWidth = 200.dp, minHeight = 300.dp, maxHeight = 1080.dp, maxWidth = 1920.dp)
        .padding(20.dp)
        .clip(com.markettwits.theme.components.Shapes.medium)
        .clickable {
            isDialogOpen = true
        }

    SubcomposeAsyncImage(
        model = imageUrl,
        contentScale = ContentScale.Fit,
        contentDescription = "",
        loading = {
            ImageLoading()
            onChangeImageState(ImageState.Loading)
        }, success = {
            SubcomposeAsyncImageContent(modifier = innerModifier)
            onChangeImageState(
                ImageState.Success(
                    id,
                    imageUrl,
                    it.result.image.width,
                    it.result.image.height,
                    false
                )
            )
        },
        error = {
            val message = it.result.throwable.toString()
            onChangeImageState(ImageState.Error(message))
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





