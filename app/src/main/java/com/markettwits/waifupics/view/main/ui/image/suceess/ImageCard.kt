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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.markettwits.waifupics.R
import com.markettwits.waifupics.core.WaifuPicsApp
import com.markettwits.waifupics.view.main.ui.image.ImageViewModel
import com.markettwits.waifupics.view.main.ui.image.zoom.FullScreenImageDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard() {

    val viewModel = (LocalContext.current.applicationContext as WaifuPicsApp).viewModel(
        checkNotNull(LocalViewModelStoreOwner.current), ImageViewModel::class.java
    )
    var isDialogOpen by remember { mutableStateOf(false) }
    val image = imageRequest(LocalContext.current)

    AsyncImage(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .wrapContentHeight()
            .clickable { isDialogOpen = true }
        ,
        contentScale = ContentScale.FillWidth,
        model = image,
        contentDescription = stringResource(R.string.waifu_image),
        onSuccess = {
            viewModel.map(it.result.drawable)
        }
    )
    if (isDialogOpen) {
        if (viewModel.fetch() != null){
            FullScreenImageDialog(
                imageUrl = viewModel.fetch()!!,
                onClose = { isDialogOpen = false }
            )
        }else{
            ImageLost()
        }
    }
}

@Composable
private fun ImageLost(){
    Toast.makeText(LocalContext.current, "Image lost", Toast.LENGTH_SHORT).show()
}

private fun imageRequest(context: Context) : ImageRequest = ImageRequest.Builder(context)
    .data("https://api.nekosapi.com/v2/images/random/file")
    .crossfade(true)
    .diskCachePolicy(CachePolicy.DISABLED)
    .build()

