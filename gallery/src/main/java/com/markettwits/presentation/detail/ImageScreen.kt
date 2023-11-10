package com.markettwits.presentation.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.presentation.detail.bottomBar.DownBarScreenImage
import com.markettwits.presentation.detail.image.ImageContent
import com.markettwits.presentation.detail.topbar.TopBarScreenImage

//@Preview
//@Composable
//private fun ImageScreenFullPreview() {
//    WaifuPicsTheme {
//        ImageScreenFull()
//    }
//}

@Composable
fun ImageScreenFull(
    modifier: Modifier = Modifier,
) {
    val viewModel: GalleryScreenViewModel.Base = ApplicationViewModel()
    val state by viewModel.state().collectAsState()
    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBarScreenImage(viewModel)
        }, bottomBar = {
            DownBarScreenImage(Modifier) {
                viewModel.delete()
            }
        }) {
        ImageContent(imageUrl = state.imageUrl(), paddingValues = it)
    }
}


private fun loadImageBitmap(imagePath: String): Bitmap? {
    return try {
        BitmapFactory.decodeFile(imagePath)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

