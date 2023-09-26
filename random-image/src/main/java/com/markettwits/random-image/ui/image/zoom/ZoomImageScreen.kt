package com.markettwits.waifupics.view.main.ui.image.zoom

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun FullScreenImageDialog(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onClose: () -> Unit
) {
    val zoomState = rememberZoomState()
    Dialog(
        onDismissRequest = { onClose() },
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        ElevatedCard(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = zoomState.scale,
                    scaleY = zoomState.scale
                )

        ) {
            AsyncImage(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .zoomable(zoomState),
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    WaifuPicsTheme {

    }
}
