package com.markettwits.core_ui.components.design

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.WaifuSpacing
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.core.core_ui.generated.resources.*

/** Shared information sections. The parent owns vertical scrolling; the palette reaches both edges. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WaifuImageMetadataSections(imageKey: Any, artistName: String?, ageRating: String?, sourceUrl: String?,
    tags: List<String>, colorPalette: List<List<Int>>, onCopy: suspend (String) -> Unit,
    modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val scope = rememberCoroutineScope()
    var linkError by remember(imageKey) { mutableStateOf(false) }
    var copyError by remember(imageKey) { mutableStateOf(false) }
    var copying by remember(imageKey) { mutableStateOf(false) }
    var copiedValue by remember(imageKey) { mutableStateOf<String?>(null) }
    var copyVersion by remember(imageKey) { mutableIntStateOf(0) }
    val copyAction by rememberUpdatedState(onCopy)
    val source = sourceUrl.orEmpty().trim()
    val host = imageSourceHost(source)
    val palette = remember(colorPalette) { imagePaletteSwatches(colorPalette) }
    val cleanTags = remember(tags) { tags.map(String::trim).filter(String::isNotEmpty).distinct() }
    val copyText: (String) -> Unit = { text ->
        if (!copying) scope.launch(start = CoroutineStart.UNDISPATCHED) {
            copying = true
            copyError = false
            try {
                copyAction(text)
                copiedValue = text
                copyVersion++
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                copiedValue = null
                copyError = true
            } finally { copying = false }
        }
    }
    LaunchedEffect(imageKey, copyVersion) {
        if (copiedValue != null) { delay(2500); copiedValue = null }
    }
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.section)) {
        FlowRow(Modifier.fillMaxWidth().padding(horizontal = WaifuSpacing.screen),
            maxItemsInEachRow = if (LocalDensity.current.fontScale > 1.3f) 1 else 2,
            horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.screen),
            verticalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
            WaifuInfoField(stringResource(Res.string.image_author),
                artistName?.trim()?.takeIf(String::isNotEmpty) ?: stringResource(Res.string.image_not_provided), Modifier.weight(1f))
            WaifuInfoField(stringResource(Res.string.age_rating), waifuImageRatingLabel(ageRating), Modifier.weight(1f))
        }
        HorizontalDivider(Modifier.padding(horizontal = WaifuSpacing.screen), color = MaterialTheme.colorScheme.outlineVariant)
        if (copyError) MetadataError(stringResource(Res.string.image_copy_failed), Modifier.padding(horizontal = WaifuSpacing.screen))
        Column(verticalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
            WaifuSectionLabel(stringResource(Res.string.image_palette_title), Modifier.padding(horizontal = WaifuSpacing.screen))
            if (palette.isEmpty()) InformationText(stringResource(Res.string.image_not_provided), Modifier.padding(horizontal = WaifuSpacing.screen))
            else {
                Text(stringResource(Res.string.image_palette_copy_hint), Modifier.padding(horizontal = WaifuSpacing.screen),
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = WaifuSpacing.screen),
                    horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
                    palette.forEach { swatch -> PaletteTile(swatch, copiedValue == swatch.hex, !copying) { copyText(swatch.hex) } }
                }
            }
        }
        Column(Modifier.padding(horizontal = WaifuSpacing.screen), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
            WaifuSectionLabel(stringResource(Res.string.image_source))
            if (host != null) {
                Text(host, style = MaterialTheme.typography.titleMedium)
                SelectionContainer {
                    Text(source, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
                FlowRow(Modifier.fillMaxWidth(), maxItemsInEachRow = if (LocalDensity.current.fontScale > 1.3f) 1 else 2,
                    horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.item),
                    verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                    WaifuActionButton(stringResource(Res.string.open_image_source),
                        { linkError = runCatching { uriHandler.openUri(source) }.isFailure },
                        Modifier.weight(1f), icon = Icons.Outlined.OpenInNew)
                    WaifuActionButton(stringResource(if (copiedValue == source) Res.string.image_copied else Res.string.copy_image_source),
                        { copyText(source) }, Modifier.weight(1f), enabled = !copying,
                        icon = if (copiedValue == source) Icons.Default.Check else Icons.Outlined.ContentCopy,
                        style = WaifuActionStyle.Outlined)
                }
                if (linkError) MetadataError(stringResource(Res.string.image_source_open_failed))
            } else InformationText(stringResource(Res.string.image_not_provided))
        }
        Column(Modifier.padding(horizontal = WaifuSpacing.screen), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
            WaifuSectionLabel(stringResource(Res.string.image_tags))
            if (cleanTags.isEmpty()) InformationText(stringResource(Res.string.image_not_provided))
            else SelectionContainer {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.small), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                    cleanTags.forEach { WaifuTag(it) }
                }
            }
        }
    }
}

internal fun imageSourceHost(source: String): String? {
    if (!(source.startsWith("https://", true) || source.startsWith("http://", true)) || source.any { it.isWhitespace() }) return null
    val host = source.substringAfter("://", "").substringBefore('/').substringBefore('?').substringBefore('#')
    return host.takeIf(String::isNotBlank)?.removePrefix("www.")
}

internal data class ImagePaletteSwatch(val color: Color, val hex: String)
internal fun imagePaletteSwatches(palette: List<List<Int>>) = palette.filter { it.size >= 3 }.map { rgb ->
    val channels = rgb.take(3).map { it.coerceIn(0, 255) }
    ImagePaletteSwatch(Color(channels[0], channels[1], channels[2]),
        "#" + channels.joinToString("") { it.toString(16).padStart(2, '0').uppercase() })
}.distinctBy { it.hex }

@Composable
fun waifuImageRatingLabel(rating: String?): String = when (rating) {
    "safe" -> stringResource(Res.string.safe_for_work)
    "suggestive" -> stringResource(Res.string.suggestive)
    "borderline" -> stringResource(Res.string.borderline)
    "explicit" -> stringResource(Res.string.explicit_18)
    else -> rating?.takeIf(String::isNotBlank) ?: stringResource(Res.string.image_not_provided)
}

@Composable
private fun PaletteTile(swatch: ImagePaletteSwatch, copied: Boolean, enabled: Boolean, onCopy: () -> Unit) {
    val description = stringResource(Res.string.copy_palette_color, swatch.hex)
    val state = if (copied) stringResource(Res.string.image_copied) else ""
    Surface(onClick = onCopy, enabled = enabled,
        modifier = Modifier.width(64.dp * maxOf(1f, LocalDensity.current.fontScale)).heightIn(min = 56.dp)
            .semantics { contentDescription = description; stateDescription = state },
        shape = MaterialTheme.shapes.small, color = Color.Transparent, contentColor = MaterialTheme.colorScheme.onSurface) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(Modifier.fillMaxWidth().height(32.dp).clip(MaterialTheme.shapes.small).background(swatch.color), contentAlignment = Alignment.Center) {
                if (copied) Icon(Icons.Default.Check, null, Modifier.size(18.dp),
                    tint = if (swatch.color.luminance() > 0.179f) Color.Black else Color.White)
            }
            Text(swatch.hex, style = MaterialTheme.typography.labelSmall, maxLines = 1)
        }
    }
}

@Composable
private fun InformationText(value: String, modifier: Modifier = Modifier) {
    SelectionContainer { Text(value, modifier, style = MaterialTheme.typography.bodyMedium) }
}

@Composable
private fun MetadataError(value: String, modifier: Modifier = Modifier) {
    Text(value, modifier.semantics { liveRegion = LiveRegionMode.Polite },
        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
}
