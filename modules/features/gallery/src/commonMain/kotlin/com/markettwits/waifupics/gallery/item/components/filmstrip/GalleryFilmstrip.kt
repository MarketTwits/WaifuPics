package com.markettwits.waifupics.gallery.item.components.filmstrip

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.markettwits.waifupics.gallery.common.rememberGalleryImageRequest
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*
import kotlin.math.abs

/** User scrolling previews photos; only the final centered item is committed to the pager. */
@Composable
internal fun GalleryFilmstrip(images: List<ImageFavoriteUi>, selectedIndex: Int,
    onPreview: (Int?) -> Unit, onSelect: (Int, Boolean) -> Unit) {
    if (images.size <= 1) return
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex.coerceIn(images.indices))
    val dragged by listState.interactionSource.collectIsDraggedAsState()
    var scrubbing by remember { mutableStateOf(false) }
    var focused by remember { mutableIntStateOf(selectedIndex) }
    val preview by rememberUpdatedState(onPreview)
    val select by rememberUpdatedState(onSelect)
    val haptic = LocalHapticFeedback.current
    val ids = remember(images) { images.map { it.id } }
    val nearest by remember(listState) {
        derivedStateOf {
            val info = listState.layoutInfo
            val center = (info.viewportStartOffset + info.viewportEndOffset) / 2
            info.visibleItemsInfo.minByOrNull { abs(it.offset + it.size / 2 - center) }?.index ?: selectedIndex
        }
    }
    LaunchedEffect(listState, ids) {
        snapshotFlow { Triple(dragged, listState.isScrollInProgress, nearest) }.collect { (drag, scrolling, index) ->
            if (drag) scrubbing = true
            if (scrubbing) {
                val target = index.coerceIn(images.indices)
                if (focused != target) {
                    focused = target
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                }
                preview(target)
                if (!drag && !scrolling) {
                    select(target, false)
                    scrubbing = false
                }
            }
        }
    }
    LaunchedEffect(selectedIndex, ids, scrubbing) {
        if (!scrubbing) {
            focused = selectedIndex.coerceIn(images.indices)
            listState.animateScrollToItem(focused)
        }
    }
    DisposableEffect(Unit) { onDispose { preview(null) } }
    BoxWithConstraints(Modifier.fillMaxWidth().height(64.dp)) {
        val extent = 48.dp
        val padding = ((maxWidth - extent) / 2).coerceAtLeast(0.dp)
        LazyRow(state = listState, modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = padding),
            verticalAlignment = Alignment.CenterVertically,
            flingBehavior = rememberFilmstripFlingBehavior()) {
            itemsIndexed(images, key = { _, image -> image.id }) { index, image ->
                val active = index == focused
                val width by animateDpAsState(if (active) 40.dp else 32.dp, tween(180), label = "Thumbnail width")
                val height by animateDpAsState(if (active) 52.dp else 42.dp, tween(180), label = "Thumbnail height")
                val description = stringResource(Res.string.gallery_viewer_photo, index + 1, images.size)
                Box(Modifier.width(extent).fillMaxHeight().selectable(active, role = Role.Tab, onClick = {
                    if (!scrubbing) {
                        select(index, true)
                    }
                }).semantics { contentDescription = description }, contentAlignment = Alignment.Center) {
                    AsyncImage(model = rememberGalleryImageRequest(image), contentDescription = null, contentScale = ContentScale.Crop,
                        modifier = Modifier.size(width, height).clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .border(if (active) 2.dp else 0.dp,
                                if (active) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent,
                                MaterialTheme.shapes.small))
                }
            }
        }
    }
}
