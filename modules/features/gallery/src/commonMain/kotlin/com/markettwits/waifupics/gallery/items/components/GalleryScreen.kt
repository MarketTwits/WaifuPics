package com.markettwits.waifupics.gallery.items.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.gallery.items.components.empty_item.EmptyGalleryItem
import com.markettwits.waifupics.gallery.items.components.animations.GalleryImageTransition
import com.markettwits.waifupics.gallery.items.components.list_item.MediaComponent
import com.markettwits.waifupics.gallery.items.components.picker.SelectionSheet
import com.markettwits.waifupics.gallery.items.model.*
import com.markettwits.waifupics.gallery.items.viewmodel.GalleryViewModel
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*
import kotlin.math.ceil
import kotlin.math.roundToInt

@Composable
fun GalleryScreen(onClickGoDetail: () -> Unit, paddingValues: PaddingValues,
    imageTransition: GalleryImageTransition? = null, returnToImageId: Int? = null) {
    val viewModel: GalleryViewModel = ApplicationViewModel()
    val state by viewModel.state().collectAsState()
    val selected by viewModel.selectedState().collectAsState()
    val selectedMedia = viewModel.selectedPhotoState()
    val layout by viewModel.layout().collectAsState()
    var showLayout by rememberSaveable { mutableStateOf(false) }
    var pendingAnchor by remember { mutableStateOf<Int?>(null) }
    val selectedIds = selectedMedia.map { it.id }.toSet()
    val haptic = LocalHapticFeedback.current
    val gridState = rememberLazyGridState()
    val overscroll = rememberGalleryOverscrollEffect(gridState)
    val gridDragged by gridState.interactionSource.collectIsDraggedAsState()
    val today = rememberGalleryToday()
    val zone = TimeZone.currentSystemDefault()
    val sections = remember(state.items, zone, layout.byMonth) { gallerySections(state.items, zone, layout.byMonth) }
    val ids = remember(sections) { sections.flatMap { it.images }.map { it.id } }
    val navigationInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val bottomPadding = (if (selected) 180.dp else WaifuSpacing.content) + navigationInset

    BoxWithConstraints(Modifier.padding(top = paddingValues.calculateTopPadding()).fillMaxSize().clipToBounds()) {
        val density = LocalDensity.current
        val contentWidth = with(density) { maxWidth.roundToPx() - 2 * WaifuSpacing.content.roundToPx() }
            .coerceAtLeast(1)
        val spacing = with(density) { WaifuSpacing.small.roundToPx() }
        val automaticColumns = ((contentWidth + spacing) / with(density) { (130.dp + WaifuSpacing.small).roundToPx() })
            .coerceAtLeast(1)
        val capacity = ((contentWidth + spacing) / with(density) { (36.dp + WaifuSpacing.small).roundToPx() }).coerceAtLeast(1)
        val columns = (layout.columns.takeIf { it in 2..6 } ?: automaticColumns).coerceIn(1, capacity)
        val cellWidth = ceil((contentWidth - spacing * (columns - 1)).toDouble() / columns).toInt()
        val headerHeight = with(density) { GalleryDateHeaderHeight.roundToPx() }
        val footerHeight = with(density) { bottomPadding.roundToPx() }
        val viewport = with(density) { maxHeight.roundToPx() }
        val justifiedRows = remember(sections, layout.keepAspectRatio, contentWidth, columns, spacing, density) {
            if (layout.keepAspectRatio) sections.associate { section ->
                section.key to galleryJustifiedRows(section.images, contentWidth,
                    minOf((cellWidth / 0.8f).roundToInt(), with(density) { 280.dp.roundToPx() }),
                    spacing, with(density) { 48.dp.roundToPx() })
            } else null
        }
        val geometry = remember(sections, columns, cellWidth, spacing, headerHeight, footerHeight, viewport, justifiedRows) {
            GalleryScrollGeometry(sections, columns, headerHeight, (cellWidth / 0.8f).roundToInt(),
                spacing, spacing, footerHeight, viewport, justifiedRows)
        }
        LaunchedEffect(layout, geometry) {
            pendingAnchor?.let { id -> geometry.itemForPhoto(id)?.let { gridState.requestScrollToItem(it) } }
            pendingAnchor = null
        }
        val returnOffset = headerHeight + spacing
        LaunchedEffect(imageTransition?.visible, returnToImageId, geometry) {
            if (imageTransition?.visible != false && returnToImageId != null) {
                val index = geometry.itemForPhoto(returnToImageId)
                if (index != null && gridState.layoutInfo.visibleItemsInfo.none { it.index == index }) {
                    gridState.requestScrollToItem(index, -returnOffset)
                }
            }
        }
        val pinnedSection by remember(gridState, geometry) {
            derivedStateOf { geometry.pinnedSection(gridState.firstVisibleItemIndex, gridState.firstVisibleItemScrollOffset) }
        }
        LaunchedEffect(gridState, geometry) {
            var previous: String? = null
            snapshotFlow { pinnedSection?.key to gridDragged }.collect { (current, dragged) ->
                if (dragged && previous != null && current != null && current != previous) {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                }
                previous = current
            }
        }
        val selectSection: (GallerySection) -> Unit = { section ->
            viewModel.selectGroup(section.images.map { it.id }.toSet())
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
        if (sections.isEmpty()) {
            EmptyGalleryItem()
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(if (layout.keepAspectRatio) 1 else columns), state = gridState, overscrollEffect = overscroll,
                modifier = Modifier.fillMaxSize()
                    .gallerySelectionGesture(gridState, ids, selectedIds, selected,
                        onSelection = { viewModel.replaceSelection(it, keepMode = true) },
                        onFinish = viewModel::finishSelectionGesture,
                        viewportTranslationY = { overscroll.displacement },
                        obscuredTop = { if (pinnedSection != null) headerHeight.toFloat() else 0f },
                        rowPhotoAt = if (layout.keepAspectRatio) geometry::photoAt else null),
                horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.small),
                verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small),
                contentPadding = PaddingValues(start = WaifuSpacing.content, end = WaifuSpacing.content,
                    top = WaifuSpacing.small, bottom = bottomPadding)) {
                sections.forEach { section ->
                    item(key = "date-${section.key}", contentType = "date", span = { GridItemSpan(maxLineSpan) }) {
                        GalleryDateHeader(
                            label = if (layout.byMonth) galleryMonthLabel(section.date, today) else galleryDateLabel(section.date, today),
                            count = section.images.size,
                            selectedCount = section.images.count { it.id in selectedIds },
                            onSelectGroup = { selectSection(section) },
                            onLayout = if (section === sections.first() && pinnedSection == null) ({ showLayout = true }) else null)
                    }
                    if (justifiedRows != null) {
                        items(justifiedRows.getValue(section.key), key = { it.key }, contentType = { "proportional-row" }) { row ->
                            GalleryJustifiedRowContent(row) { image, frame ->
                                GalleryPhoto(viewModel, image, selected, selectedIds, selectedMedia,
                                    imageTransition, onClickGoDetail, frame, preserveAspectRatio = true)
                            }
                        }
                    } else {
                        items(section.images, key = { "photo-${it.id}" }, contentType = { "image" }) { image ->
                            GalleryPhoto(viewModel, image, selected, selectedIds, selectedMedia, imageTransition,
                                onClickGoDetail, Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null))
                        }
                    }
                }
            }
            // Like Ente, the current date is a stationary overlay, not a header pushed by the next one.
            pinnedSection?.let { section ->
                GalleryPinnedDateHeader(section, layout.byMonth, today, selectedIds,
                    onSelectGroup = { selectSection(section) }, onLayout = { showLayout = true })
            }
            GalleryFastScroller(gridState, geometry, Modifier.fillMaxHeight()
                .padding(top = WaifuSpacing.small, bottom = bottomPadding), overscroll = overscroll,
                pinnedHeaderHeight = if (pinnedSection != null) GalleryDateHeaderHeight - WaifuSpacing.small else 0.dp)
        }
        if (showLayout) GalleryLayoutSheet(layout, onApply = { choice ->
            if (choice != layout) {
                pendingAnchor = gridState.layoutInfo.visibleItemsInfo.firstOrNull {
                    it.key.toString().startsWith("photo-") || it.key.toString().startsWith("row-")
                }?.let { info -> info.key.toString().removePrefix("photo-").toIntOrNull()
                    ?: geometry.firstPhotoInItem(info.index) }
                overscroll.reset()
                viewModel.setLayout(choice)
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            }
        }, onDismiss = { showLayout = false })
        SelectionSheet(modifier = Modifier.align(Alignment.BottomCenter), selectedMedia = selectedMedia,
            selectionState = selected, clearSelection = viewModel::changeSelectedState,
            shareImages = viewModel::shareImages, deleteImages = viewModel::delete)
    }
}

@Composable
private fun GalleryPhoto(viewModel: GalleryViewModel, image: ImageFavoriteUi.Base, selected: Boolean,
    selectedIds: Set<Int>, selectedMedia: List<ImageFavoriteUi>, transition: GalleryImageTransition?,
    onClickGoDetail: () -> Unit, modifier: Modifier, preserveAspectRatio: Boolean = false) {
    val haptic = LocalHapticFeedback.current
    val description = stringResource(Res.string.gallery_saved_image, image.created)
    MediaComponent(media = image, selectionState = selected, selectedMedia = selectedMedia,
        imageTransition = transition, preserveAspectRatio = preserveAspectRatio,
        modifier = modifier.testTag("gallery-image-${image.id}").semantics { contentDescription = description },
        onItemLongClick = {
            viewModel.replaceSelection(selectedIds + it.id, keepMode = true)
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        }, onItemClick = {
            if (selected) viewModel.selection(it.id)
            else if (transition?.running != true && transition?.visible != false) {
                viewModel.toDetail(it)
                onClickGoDetail()
            }
        })
}
