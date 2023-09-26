package com.markettwits.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme

@Composable
@Preview
private fun GalleryScreenPreview() {
    WaifuPicsTheme {
        GalleryScreen()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GalleryScreen() {
    val viewModel: GalleryViewModel = ApplicationViewModel()
    val state by viewModel.state().collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
        ) {
            items(state) {
                ItemGallery(it)
            }
        }
    }
}

@Composable
private fun ItemGallery(item: ImageFavoriteUi) {
    AsyncImage(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .height(100.dp)
            .width(350.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        model = item.imageUrl,
        contentScale = ContentScale.Crop,
        contentDescription = ""
    )
}