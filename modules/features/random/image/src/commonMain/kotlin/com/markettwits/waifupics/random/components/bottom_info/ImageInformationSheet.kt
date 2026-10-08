package com.markettwits.waifupics.random.components.bottom_info

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.unit.sp
import com.markettwits.theme.components.FontRubik
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
    val foreground = MaterialTheme.colorScheme.inversePrimary
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

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = foreground,
        dragHandle = { BottomSheetDefaults.DragHandle(color = foreground.copy(alpha = 0.25f)) }
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(Res.string.image_information), Modifier.weight(1f),
                    fontFamily = FontRubik.medium(), fontSize = 20.sp, lineHeight = 26.sp)
                IconButton(onClick = onDismiss, modifier = Modifier.size(48.dp)) {
                    Icon(Icons.Default.Close, stringResource(Res.string.close_image_information), Modifier.size(20.dp))
                }
            }
            if (copyError) Text(stringResource(Res.string.image_copy_failed),
                modifier = Modifier.padding(horizontal = 20.dp).padding(top = 8.dp)
                    .semantics { liveRegion = LiveRegionMode.Polite },
                fontFamily = FontRubik.regular(), fontSize = 13.sp, lineHeight = 18.sp)
            Column(
                Modifier.fillMaxWidth().weight(1f, fill = false).verticalScroll(rememberScrollState())
                    .padding(top = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    InformationField(stringResource(Res.string.image_author),
                        image.imageData.artistName?.takeIf { it.isNotBlank() }
                            ?: stringResource(Res.string.image_not_provided))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        InformationField(stringResource(Res.string.image_size),
                            loadedImage?.let { "${it.width} × ${it.height}" }
                                ?: stringResource(Res.string.image_size_pending), Modifier.weight(1f))
                        InformationField(stringResource(Res.string.age_rating),
                            imageRatingLabel(image.imageData.ageRating), Modifier.weight(1f))
                    }
                }
                HorizontalDivider(Modifier.padding(horizontal = 20.dp), color = foreground.copy(alpha = 0.1f))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    FieldLabel(stringResource(Res.string.image_palette_title), Modifier.padding(horizontal = 20.dp))
                    if (palette.isEmpty()) Box(Modifier.padding(horizontal = 20.dp)) {
                        InformationText(stringResource(Res.string.image_not_provided))
                    }
                    else {
                        Text(stringResource(Res.string.image_palette_copy_hint),
                            modifier = Modifier.padding(horizontal = 20.dp),
                            color = foreground.copy(alpha = 0.65f), fontFamily = FontRubik.regular(),
                            fontSize = 12.sp, lineHeight = 18.sp)
                        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            palette.forEach { swatch ->
                                PaletteTile(swatch, copiedValue == swatch.hex, !copying) { copyText(swatch.hex) }
                            }
                        }
                    }
                }
                Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    FieldLabel(stringResource(Res.string.image_source))
                    if (hasSource) {
                        Text(host.removePrefix("www."), fontSize = 16.sp, lineHeight = 22.sp,
                            fontFamily = FontRubik.medium())
                        SelectionContainer {
                            Text(source, fontSize = 13.sp, lineHeight = 19.sp,
                                fontFamily = FontRubik.regular(), color = foreground.copy(alpha = 0.7f),
                                maxLines = 2, overflow = TextOverflow.Ellipsis)
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            FilledTonalButton(
                                onClick = { linkError = runCatching { uriHandler.openUri(source) }.isFailure },
                                modifier = Modifier.weight(1f).heightIn(min = 48.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = foreground.copy(alpha = 0.07f), contentColor = foreground)
                            ) {
                                Icon(Icons.Outlined.OpenInNew, null, Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(stringResource(Res.string.open_image_source), fontFamily = FontRubik.medium(),
                                    fontSize = 13.sp, lineHeight = 18.sp)
                            }
                            OutlinedButton(
                                onClick = { copyText(source) }, enabled = !copying,
                                modifier = Modifier.weight(1f).heightIn(min = 48.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
                                border = BorderStroke(1.dp, foreground.copy(alpha = 0.2f)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = foreground)
                            ) {
                                Icon(if (copiedValue == source) Icons.Default.Check else Icons.Outlined.ContentCopy,
                                    null, Modifier.size(18.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(stringResource(if (copiedValue == source) Res.string.image_copied else Res.string.copy_image_source),
                                    fontFamily = FontRubik.medium(), fontSize = 13.sp, lineHeight = 18.sp)
                            }
                        }
                        if (linkError) Text(stringResource(Res.string.image_source_open_failed),
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                            fontFamily = FontRubik.regular(), fontSize = 13.sp)
                    } else InformationText(stringResource(Res.string.image_not_provided))
                }
                Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    FieldLabel(stringResource(Res.string.image_tags))
                    if (tags.isEmpty()) InformationText(stringResource(Res.string.image_not_provided))
                    else SelectionContainer {
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            tags.forEach { tag ->
                                Surface(shape = RoundedCornerShape(8.dp),
                                    color = foreground.copy(alpha = 0.06f), contentColor = foreground) {
                                    Text(tag, Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        fontFamily = FontRubik.regular(), fontSize = 13.sp, lineHeight = 18.sp)
                                }
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
    val foreground = MaterialTheme.colorScheme.inversePrimary
    val description = stringResource(Res.string.copy_palette_color, swatch.hex)
    val state = if (copied) stringResource(Res.string.image_copied) else ""
    Surface(
        onClick = onCopy, enabled = enabled,
        modifier = Modifier.width(64.dp * maxOf(1f, LocalDensity.current.fontScale))
            .heightIn(min = 56.dp)
            .semantics { contentDescription = description; stateDescription = state },
        shape = RoundedCornerShape(6.dp),
        color = Color.Transparent, contentColor = foreground
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(Modifier.fillMaxWidth().height(32.dp).clip(RoundedCornerShape(6.dp)).background(swatch.color),
                contentAlignment = Alignment.Center) {
                if (copied) Icon(Icons.Default.Check, null, Modifier.size(18.dp),
                    tint = if (swatch.color.luminance() > 0.179f) Color.Black else Color.White)
            }
            Text(swatch.hex, fontFamily = FontRubik.medium(), fontSize = 11.sp, lineHeight = 16.sp, maxLines = 1)
        }
    }
}

@Composable
private fun InformationField(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        FieldLabel(label)
        InformationText(value)
    }
}

@Composable
private fun FieldLabel(label: String, modifier: Modifier = Modifier) {
    Text(label, modifier, fontSize = 12.sp, lineHeight = 16.sp, fontFamily = FontRubik.medium(),
        color = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.65f))
}

@Composable
private fun InformationText(value: String) {
    SelectionContainer {
        Text(value, fontSize = 15.sp, lineHeight = 21.sp, fontFamily = FontRubik.regular())
    }
}
