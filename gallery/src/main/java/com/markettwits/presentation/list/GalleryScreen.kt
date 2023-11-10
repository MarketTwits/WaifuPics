package com.markettwits.presentation.list

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.navigation.LocalNavigationState
import com.markettwits.navigation.Screen
import com.markettwits.presentation.detail.ImageFavoriteUi
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme
import run.nabla.gallerypicker.picker.rememberGalleryPickerState

@Composable
@Preview
private fun GalleryScreenPreview() {
    WaifuPicsTheme {
        GalleryScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GalleryScreen() {
    var isInSelectionMode by remember {
        mutableStateOf(false)
    }
    val selectedItems = remember {
        mutableStateListOf<ImageFavoriteUi>()
    }
    val context = LocalContext.current
    //val navigationState = LocalNavigation.current
    var isDialogOpen by remember { mutableStateOf("") }
    val viewModel: GalleryViewModel.Base = ApplicationViewModel()
    val state by viewModel.state().collectAsState()
    val imageState = rememberGalleryPickerState()
    viewModel.favoriteImages()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
        ) {
            items(state, key = {it.id()}) { image ->
                image.GalleryItem(Modifier.combinedClickable(
                    onClick = {
                        viewModel.toDetail(image)
                        LocalNavigationState.rootNavigation.getNavController.navigate(
                            Screen.GalleryItem.route()
                        )
                    },
                    onLongClick = {
                        viewModel.deleteImage(image.imageUrl(), image.id())
                    }
                ), imageState)
            }
        }
        //if (isDialogOpen != "") ImageScreenFull(state. = isDialogOpen)
    }

}
