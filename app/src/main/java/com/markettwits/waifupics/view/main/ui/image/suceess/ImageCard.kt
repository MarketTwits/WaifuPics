package com.markettwits.waifupics.view.main.ui.image.suceess

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.Coil
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.util.CoilUtils
import com.markettwits.waifupics.R
import com.markettwits.waifupics.core.WaifuPicsApp
import com.markettwits.waifupics.view.main.data.net.models.RandomImageCloud
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel
import com.markettwits.waifupics.view.main.ui.image.zoom.FullScreenImageDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(state: RandomImageCloud) {
    var url = rememberSaveable {
        mutableStateOf(state.data.attributes.file)
    }
    var isDialogOpen by remember { mutableStateOf(false) }
    val request = imageRequest(LocalContext.current, url.value)

    AsyncImage(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .wrapContentHeight()
            .clickable { isDialogOpen = true },
        contentScale = ContentScale.FillWidth,
        model = request,
        contentDescription = stringResource(R.string.waifu_image),
    )
    if (isDialogOpen) {
        FullScreenImageDialog(
            imageUrl = url.value,
            onClose = { isDialogOpen = false }
        )
    }
}


@Composable
private fun ImageLost() {
    Toast.makeText(LocalContext.current, "Image lost", Toast.LENGTH_SHORT).show()
}

private fun imageRequest(context: Context, url: String): ImageRequest =
    ImageRequest.Builder(context)
        .data(url)
        .crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .memoryCacheKey("mt05s")
        .build()
fun clearCache(context: Context, memory: Boolean = true) {
    // 1) clear memory cache
    if (memory) {
        val imageLoader = context.imageLoader
        imageLoader.memoryCache?.clear()
    }
    // 2) clear file cache
}

