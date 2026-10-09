package com.markettwits.waifupics.gallery.items.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.gallery.items.components.empty_item.EmptyGalleryItem
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
fun GalleryScreen(onClickGoDetail: () -> Unit, paddingValues: PaddingValues) {
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
    val gridDragged by gridState.interactionSource.collectIsDraggedAsState()
    val today = rememberGalleryToday()
    val zone = TimeZone.currentSystemDefault()
    val sections = remember(state.items, zone, layout.byMonth) { gallerySections(state.items, zone, layout.byMonth) }
    val ids = remember(sections) { sections.flatMap { it.images }.map { it.id } }
    val photoIndexes = remember(sections) {
        buildMap<Int, Int> {
            var index = 0
            sections.forEach { section ->
                index++
                section.images.forEach { put(it.id, index++) }
            }
        }
    }
    LaunchedEffect(layout) {
        pendingAnchor?.let { photoIndexes[it]?.let(gridState::requestScrollToItem) }
        pendingAnchor = null
    }
    val navigationInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val bottomPadding = (if (selected) 180.dp else WaifuSpacing.content) + navigationInset

    BoxWithConstraints(Modifier.padding(top = paddingValues.calculateTopPadding()).fillMaxSize()) {
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
        val geometry = remember(sections, columns, cellWidth, spacing, headerHeight, footerHeight, viewport) {
            GalleryScrollGeometry(sections, columns, headerHeight, (cellWidth / 0.8f).roundToInt(),
                spacing, spacing, footerHeight, viewport)
        }
        val activeSectionKey by remember(gridState, geometry) {
            derivedStateOf { geometry.sectionOfItem(gridState.firstVisibleItemIndex)?.key }
        }
        LaunchedEffect(gridState, geometry) {
            var previous: String? = null
            snapshotFlow { gridState.firstVisibleItemIndex to gridDragged }.collect { (index, dragged) ->
                val current = geometry.sectionOfItem(index)?.key
                if (dragged && previous != null && current != previous) {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                }
                previous = current
            }
        }
        if (sections.isEmpty()) {
            EmptyGalleryItem()
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(columns), state = gridState,
                modifier = Modifier.fillMaxSize()
                    .gallerySelectionGesture(gridState, ids, selectedIds, selected,
                        onSelection = { viewModel.replaceSelection(it, keepMode = true) },
                        onFinish = viewModel::finishSelectionGesture),
                horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.small),
                verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small),
                contentPadding = PaddingValues(start = WaifuSpacing.content, end = WaifuSpacing.content,
                    top = WaifuSpacing.small, bottom = bottomPadding)) {
                sections.forEach { section ->
                    stickyHeader(key = "date-${section.key}", contentType = "date") {
                        GalleryDateHeader(
                            label = if (layout.byMonth) galleryMonthLabel(section.date, today) else galleryDateLabel(section.date, today),
                            count = section.images.size,
                            selectedCount = section.images.count { it.id in selectedIds },
                            onSelectGroup = {
                                viewModel.selectGroup(section.images.map { it.id }.toSet())
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            }, onLayout = if (section.key == activeSectionKey) ({ showLayout = true }) else null)
                    }
                    items(section.images, key = { "photo-${it.id}" }, contentType = { "image" }) { image ->
                        val description = stringResource(Res.string.gallery_saved_image,
                            image.created)
                        MediaComponent(media = image, selectionState = selected,
                            selectedMedia = selectedMedia,
                            modifier = Modifier.testTag("gallery-image-${image.id}")
                                .semantics { contentDescription = description },
                            onItemLongClick = {
                                viewModel.replaceSelection(selectedIds + it.id, keepMode = true)
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            }, onItemClick = {
                                if (selected) viewModel.selection(it.id)
                                else {
                                    viewModel.toDetail(it)
                                    onClickGoDetail()
                                }
                            })
                    }
                }
            }
            GalleryFastScroller(gridState, geometry, Modifier.fillMaxHeight()
                .padding(top = WaifuSpacing.small, bottom = bottomPadding))
        }
        if (showLayout) GalleryLayoutSheet(layout, onApply = { choice ->
            if (choice != layout) {
                pendingAnchor = gridState.layoutInfo.visibleItemsInfo.firstOrNull { it.key.toString().startsWith("photo-") }
                    ?.key?.toString()?.removePrefix("photo-")?.toIntOrNull()
                viewModel.setLayout(choice)
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            }
        }, onDismiss = { showLayout = false })
        SelectionSheet(modifier = Modifier.align(Alignment.BottomCenter), selectedMedia = selectedMedia,
            selectionState = selected, clearSelection = viewModel::changeSelectedState,
            shareImages = viewModel::shareImages, deleteImages = viewModel::delete)
    }
}
