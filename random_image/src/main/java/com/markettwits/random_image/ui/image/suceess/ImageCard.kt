package com.markettwits.random_image.ui.image.suceess

import android.annotation.SuppressLint
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
import com.markettwits.core_ui.R
import com.markettwits.core_ui.image.LocalImageLoader
import com.markettwits.waifupics.view.main.ui.image.zoom.FullScreenImageDialog


@SuppressLint("RememberReturnType")
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
        model = LocalImageLoader.current.memorySingle(imageUrl),
        contentDescription = stringResource(R.string.waifu_image),
    )
    if (isDialogOpen) {
        FullScreenImageDialog(
            imageUrl = imageUrl,
            onClose = { isDialogOpen = false }
        )
    }
}
