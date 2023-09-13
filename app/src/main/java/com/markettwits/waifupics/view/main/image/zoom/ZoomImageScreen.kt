package com.markettwits.waifupics.view.main.image.zoom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.markettwits.waifupics.ui.theme.WaifuPicsTheme

@Composable
fun ZoomImageScreen() {
    Surface {

    }
}

@Composable
fun FullScreenImageDialog(imageUrl: ImageRequest, onClose: () -> Unit) {
    val density = LocalDensity.current.density
    val dialogWidth = (300 * density).dp
    val dialogHeight = (400 * density).dp

    Dialog(
        onDismissRequest = { onClose() },
        content = {
            Box(
                modifier = Modifier
                    .size(dialogWidth, dialogHeight)
                    .background(Color.Black)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrl, contentDescription = null, modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    )
}
@Preview
@Composable
private fun Preview() {
    WaifuPicsTheme {
        //FullScreenImageDialog(painterResource()) {}
    }
}

