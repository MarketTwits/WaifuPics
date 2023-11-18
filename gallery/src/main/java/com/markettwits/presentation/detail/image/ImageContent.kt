package com.markettwits.presentation.detail.image

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.markettwits.core_ui.image.LocalImageLoader
import com.markettwits.presentation.detail.GalleryScreenViewModel
import com.markettwits.presentation.detail.ImageFavoriteUi
import net.engawapg.lib.zoomable.ZoomState
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable


@Composable
fun ImageContent(imageUrl : String,paddingValues: PaddingValues, setCurrentItem : () -> Unit){
    AsyncImage(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        onState = {
            Log.d("mt05", "#ImageContent $imageUrl")
        },
        model = LocalImageLoader.current.single(imageUrl),
        contentScale = ContentScale.Fit,
        contentDescription = ""
    )
    setCurrentItem()
}