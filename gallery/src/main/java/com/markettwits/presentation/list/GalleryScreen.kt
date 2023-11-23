package com.markettwits.presentation.list

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.navigation.LocalNavigationState
import com.markettwits.navigation.Screen
import run.nabla.gallerypicker.picker.rememberGalleryPickerState

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GalleryScreen() {

    val viewModel: GalleryViewModel.Base = ApplicationViewModel()
    val state by viewModel.state().collectAsState()
    val imageState = rememberGalleryPickerState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
        ) {
            items(state, key = { it.id() }) { image ->
                image.GalleryItem(
                    Modifier.combinedClickable(
                        onClick = {
                            viewModel.toDetail(image)
                            showDetailScreen()
                        },
                        onLongClick = {
                            viewModel.deleteImage(image.imageUrl(), image.id())
                        }
                    ), imageState)
            }
        }

    }
}

fun showDetailScreen(){
    LocalNavigationState.rootNavigation.getNavController.navigate(
        Screen.GalleryItem.route()
    )
}


