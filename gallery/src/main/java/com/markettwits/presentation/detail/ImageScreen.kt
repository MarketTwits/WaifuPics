package com.markettwits.presentation.detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.markettwits.core_ui.ApplicationViewModel
import com.markettwits.navigation.LocalNavigationState
import com.markettwits.presentation.list.GalleryScreenViewModel
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme

//@Preview
//@Composable
//private fun ImageScreenFullPreview() {
//    WaifuPicsTheme {
//        ImageScreenFull()
//    }
//}
@Preview
@Composable
private fun TopBarScreenImagePreview() {
    WaifuPicsTheme {
        Column{
            TopBarScreenImage(modifier = Modifier) {}
        }
    }
}

@Composable
fun ImageScreenFull(
    modifier: Modifier = Modifier,
) {
    val viewModel: GalleryScreenViewModel.Base = ApplicationViewModel()
    val state by viewModel.state().collectAsState()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            TopBarScreenImage(modifier){
                viewModel.saveToGallery(state.imageUrl())
            }
            AsyncImage(
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxWidth(),
                model = loadImageBitmap(state.imageUrl()),
                placeholder = painterResource(id = com.markettwits.core_ui.R.drawable.debug_image),
                contentScale = ContentScale.Fit,
                contentDescription = ""
            )
            DownBarScreenImage(Modifier.weight(0.1f))
        }
    }
}

@Composable
fun TopBarScreenImage(modifier: Modifier, onClickMenu: () -> Unit) {
    Row(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Row {
            Icon(
                modifier = Modifier.clickable {
                    LocalNavigationState.rootNavigation.getNavController.popBackStack()
                },
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "created",
                modifier = Modifier.padding(start = 26.dp),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            )
        }
        Icon(
            modifier = modifier.clickable {
                onClickMenu()
            },
            imageVector = Icons.Default.Dehaze,
            contentDescription = "menu",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun DownBarScreenImage(modifier: Modifier) {
    Row(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    )
    {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = Icons.Default.Share,
            contentDescription = "share",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = Icons.Default.Delete,
            contentDescription = "delete",
            tint = MaterialTheme.colorScheme.onBackground
        )
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

