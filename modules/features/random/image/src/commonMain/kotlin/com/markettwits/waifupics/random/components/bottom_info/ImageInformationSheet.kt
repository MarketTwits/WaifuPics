package com.markettwits.waifupics.random.components.bottom_info

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.design.*
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.model.RandomImageState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
internal fun ImageInformationSheet(
    image: RandomImageState.Success,
    loadedImage: ImageState.Success?,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val uriHandler = LocalUriHandler.current
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    var linkError by remember(image.id) { mutableStateOf(false) }
    var copyError by remember(image.id) { mutableStateOf(false) }
    var copying by remember(image.id) { mutableStateOf(false) }
    var copiedValue by remember(image.id) { mutableStateOf<String?>(null) }
    var copyVersion by remember(image.id) { mutableIntStateOf(0) }
    val foreground = MaterialTheme.colorScheme.onSurface
    val source = image.imageData.sourceUrl.trim()
    val host = source.substringAfter("://", "").substringBefore('/').substringBefore('?').substringBefore('#')
    val hasSource = (source.startsWith("https://", true) || source.startsWith("http://", true)) &&
        host.isNotBlank() && source.none { it.isWhitespace() }
    val palette = remember(image.id, image.colorPalette) {
        image.colorPalette.filter { it.size >= 3 }.map { rgb ->
            val channels = rgb.take(3).map { it.coerceIn(0, 255) }
            val hex = "#" + channels.joinToString("") { it.toString(16).padStart(2, '0').uppercase() }
            PaletteSwatch(Color(channels[0], channels[1], channels[2]), hex)
        }.distinctBy { it.hex }
    }
    val tags = remember(image.id, image.imageData.tags) {
        image.imageData.tags.map { it.trim() }.filter { it.isNotEmpty() }.distinct()
    }
    val copyText: (String) -> Unit = { text ->
        if (!copying) scope.launch(start = CoroutineStart.UNDISPATCHED) {
            copying = true
            copyError = false
            try {
                checkNotNull(clipboard.nativeClipboard)
                clipboard.setClipEntry(plainTextClipEntry(text))
                copiedValue = text
                copyVersion++
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                copiedValue = null
                copyError = true
            } finally {
                copying = false
            }
        }
    }
    LaunchedEffect(image.id, copyVersion) {
        if (copiedValue != null) {
            delay(2500)
            copiedValue = null
        }
    }

    WaifuSheet(
        title = stringResource(Res.string.image_information),
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(Modifier.fillMaxWidth()) {
            if (copyError) Text(stringResource(Res.string.image_copy_failed),
                modifier = Modifier.padding(horizontal = WaifuSpacing.screen).padding(top = 8.dp)
                    .semantics { liveRegion = LiveRegionMode.Polite },
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
            Column(
                Modifier.fillMaxWidth().weight(1f, fill = false).verticalScroll(rememberScrollState())
                    .padding(top = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(WaifuSpacing.section)
            ) {
                Column(Modifier.padding(horizontal = WaifuSpacing.screen), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.content)) {
                    WaifuInfoField(stringResource(Res.string.image_author),
                        image.imageData.artistName?.takeIf { it.isNotBlank() }
                            ?: stringResource(Res.string.image_not_provided))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        WaifuInfoField(stringResource(Res.string.image_size),
                            loadedImage?.let { "${it.width} × ${it.height}" }
                                ?: stringResource(Res.string.image_size_pending), Modifier.weight(1f))
                        WaifuInfoField(stringResource(Res.string.age_rating),
                            imageRatingLabel(image.imageData.ageRating), Modifier.weight(1f))
                    }
                }
                HorizontalDivider(Modifier.padding(horizontal = WaifuSpacing.screen), color = MaterialTheme.colorScheme.outlineVariant)
                Column(verticalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
                    WaifuSectionLabel(stringResource(Res.string.image_palette_title), Modifier.padding(horizontal = WaifuSpacing.screen))
                    if (palette.isEmpty()) Box(Modifier.padding(horizontal = WaifuSpacing.screen)) {
                        InformationText(stringResource(Res.string.image_not_provided))
                    }
                    else {
                        Text(stringResource(Res.string.image_palette_copy_hint),
                            modifier = Modifier.padding(horizontal = WaifuSpacing.screen),
                            color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = WaifuSpacing.screen),
                            horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
                            palette.forEach { swatch ->
                                PaletteTile(swatch, copiedValue == swatch.hex, !copying) { copyText(swatch.hex) }
                            }
                        }
                    }
                }
                Column(Modifier.padding(horizontal = WaifuSpacing.screen), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
                    WaifuSectionLabel(stringResource(Res.string.image_source))
                    if (hasSource) {
                        Text(host.removePrefix("www."), style = MaterialTheme.typography.titleMedium)
                        SelectionContainer {
                            Text(source, style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                        FlowRow(Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.item),
                            verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small),
                            maxItemsInEachRow = if (LocalDensity.current.fontScale > 1.3f) 1 else 2) {
                            WaifuActionButton(
                                label = stringResource(Res.string.open_image_source),
                                onClick = { linkError = runCatching { uriHandler.openUri(source) }.isFailure },
                                modifier = Modifier.weight(1f),
                                icon = Icons.Outlined.OpenInNew
                            )
                            WaifuActionButton(
                                label = stringResource(if (copiedValue == source) Res.string.image_copied else Res.string.copy_image_source),
                                onClick = { copyText(source) }, enabled = !copying,
                                modifier = Modifier.weight(1f),
                                icon = if (copiedValue == source) Icons.Default.Check else Icons.Outlined.ContentCopy,
                                style = WaifuActionStyle.Outlined
                            )
                        }
                        if (linkError) Text(stringResource(Res.string.image_source_open_failed),
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                    } else InformationText(stringResource(Res.string.image_not_provided))
                }
                Column(Modifier.padding(horizontal = WaifuSpacing.screen), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
                    WaifuSectionLabel(stringResource(Res.string.image_tags))
                    if (tags.isEmpty()) InformationText(stringResource(Res.string.image_not_provided))
                    else SelectionContainer {
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            tags.forEach { tag ->
                                WaifuTag(tag)
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class PaletteSwatch(val color: Color, val hex: String)

@Composable
private fun PaletteTile(swatch: PaletteSwatch, copied: Boolean, enabled: Boolean, onCopy: () -> Unit) {
    val foreground = MaterialTheme.colorScheme.onSurface
    val description = stringResource(Res.string.copy_palette_color, swatch.hex)
    val state = if (copied) stringResource(Res.string.image_copied) else ""
    Surface(
        onClick = onCopy, enabled = enabled,
        modifier = Modifier.width(64.dp * maxOf(1f, LocalDensity.current.fontScale))
            .heightIn(min = 56.dp)
            .semantics { contentDescription = description; stateDescription = state },
        shape = MaterialTheme.shapes.small,
        color = Color.Transparent, contentColor = foreground
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(Modifier.fillMaxWidth().height(32.dp).clip(MaterialTheme.shapes.small).background(swatch.color),
                contentAlignment = Alignment.Center) {
                if (copied) Icon(Icons.Default.Check, null, Modifier.size(18.dp),
                    tint = if (swatch.color.luminance() > 0.179f) Color.Black else Color.White)
            }
            Text(swatch.hex, style = MaterialTheme.typography.labelSmall, maxLines = 1)
        }
    }
}

@Composable
private fun InformationText(value: String) {
    SelectionContainer {
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}
