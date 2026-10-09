package com.markettwits.waifupics.gallery.item.components.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.AsyncImagePainter
import com.markettwits.waifupics.gallery.common.rememberGalleryImageRequest
import com.markettwits.theme.components.WaifuSpacing
import kotlinx.coroutines.delay
import com.markettwits.core_ui.components.zoomable.ZoomState
import com.markettwits.core_ui.components.zoomable.zoomable
import com.markettwits.waifupics.gallery.items.components.animations.GalleryImageTransition
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*

@Composable
internal fun ZoomablePagerImage(image: ImageFavoriteUi, zoomState: ZoomState,
    uiEnabled: Boolean, active: Boolean, resetZoom: Boolean, frameScale: Float,
    imageTransition: GalleryImageTransition?, onItemClick: () -> Unit) {
    var contentSize by remember(image.id) { mutableStateOf(Size(image.width.toFloat(), image.height.toFloat())) }
    val painter = rememberAsyncImagePainter(model = rememberGalleryImageRequest(image), contentScale = ContentScale.Fit,
        onSuccess = {
            val size = it.painter.intrinsicSize
            if (size.width > 0 && size.height > 0) {
                contentSize = size
                zoomState.setContentSize(size)
            }
        })
    val loadState by painter.state.collectAsState()
    var loading by remember(image.id) { mutableStateOf(false) }
    LaunchedEffect(loadState) {
        loading = false
        if (loadState is AsyncImagePainter.State.Loading) { delay(350); loading = true }
    }
    LaunchedEffect(resetZoom) { if (resetZoom) zoomState.reset() }
    BoxWithConstraints(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GalleryImageBackdrop(image, uiEnabled, fallback = painter)
        val ratio = if (contentSize.width > 0 && contentSize.height > 0) contentSize.width / contentSize.height else 1f
        // Change layout bounds, so shared return starts at the actual dragged image size.
        val width = minOf(maxWidth, maxHeight * ratio) * frameScale
        val height = minOf(maxHeight, maxWidth / ratio) * frameScale
        Box(Modifier.fillMaxSize().zoomable(zoomState = zoomState, onTap = { onItemClick() }),
            contentAlignment = Alignment.Center) {
            val frame = Modifier.size(width, height)
            Image(painter, stringResource(Res.string.image),
                modifier = if (active) imageTransition?.image(frame, image.id) ?: frame else frame,
                contentScale = ContentScale.Fit)
        }
        if (active && imageTransition?.running != true) {
            AnimatedVisibility(loading) { CircularProgressIndicator(Modifier.size(32.dp), strokeWidth = 2.dp) }
            if (loadState is AsyncImagePainter.State.Error) {
                Surface(shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.surfaceContainerHigh) {
                    Column(Modifier.padding(WaifuSpacing.screen), horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                        Text(stringResource(Res.string.gallery_image_load_failed), style = MaterialTheme.typography.bodyMedium)
                        TextButton(onClick = painter::restart) { Text(stringResource(Res.string.gallery_retry)) }
                    }
                }
            }
        }
    }
}
