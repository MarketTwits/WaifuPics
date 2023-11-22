package com.markettwits.presentation.detail.image

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.markettwits.core_ui.image.LocalImageLoader


@Composable
fun ImageContent(imageUrl: String, paddingValues: PaddingValues, setCurrentItem: () -> Unit) {
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