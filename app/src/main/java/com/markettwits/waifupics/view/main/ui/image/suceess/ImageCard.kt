package com.markettwits.waifupics.view.main.ui.image.suceess

import android.content.Context
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.markettwits.waifupics.R
import com.markettwits.waifupics.view.main.ui.image.zoom.FullScreenImageDialog



@Composable
fun ImageCard(imageUrl: String) {
    var isDialogOpen by remember{ mutableStateOf(false) }

    AsyncImage(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .wrapContentHeight()
            .clickable { isDialogOpen = true },
        contentScale = ContentScale.FillWidth,
        model = imageUrl,
        contentDescription = stringResource(R.string.waifu_image),
    )
    if (isDialogOpen) {
        FullScreenImageDialog(
            imageUrl = imageUrl,
            onClose = { isDialogOpen = false }
        )
    }
}

private fun imageRequest(context: Context, url: String): ImageRequest =
    ImageRequest.Builder(context)
        .data(url)
        .crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED)
        //.memoryCacheKey("mt05s")
        .build()

