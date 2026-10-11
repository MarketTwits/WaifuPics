package com.markettwits.waifupics.gallery.items.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.ui.state.ToggleableState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.theme.components.WaifuSize
import kotlinx.coroutines.delay
import com.markettwits.waifupics.gallery.items.model.GallerySection
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
internal fun rememberGalleryToday(): LocalDate {
    val today by produceState(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) {
        while (true) {
            value = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            delay(60_000)
        }
    }
    return today
}

@Composable
internal fun galleryDateLabel(date: LocalDate?, today: LocalDate): String = when {
    date == null -> stringResource(Res.string.gallery_undated)
    date == today -> stringResource(Res.string.gallery_today)
    date.toEpochDays() == today.toEpochDays() - 1 -> stringResource(Res.string.gallery_yesterday)
    else -> stringResource(Res.string.gallery_date, date.day,
        stringArrayResource(Res.array.gallery_months)[date.month.ordinal],
        if (date.year == today.year) "" else date.year.toString()).trim()
}

@Composable
internal fun galleryPeriodLabel(date: LocalDate?): String = if (date == null)
    stringResource(Res.string.gallery_undated)
else stringResource(Res.string.gallery_period,
    stringArrayResource(Res.array.gallery_months)[date.month.ordinal], date.year)

@Composable
internal fun galleryMonthLabel(date: LocalDate?, today: LocalDate): String = when {
    date == null -> stringResource(Res.string.gallery_undated)
    date.year == today.year && date.month == today.month -> stringResource(Res.string.gallery_this_month)
    else -> galleryPeriodLabel(date)
}

internal val GalleryDateHeaderHeight = 40.dp

@Composable
internal fun GalleryDateHeader(label: String, count: Int, selectedCount: Int,
    onSelectGroup: () -> Unit, onLayout: (() -> Unit)?) {
    val selectDescription = stringResource(Res.string.gallery_select_group, label)
    val deselectDescription = stringResource(Res.string.gallery_deselect_group, label)
    val selection = when (selectedCount) {
        0 -> ToggleableState.Off
        count -> ToggleableState.On
        else -> ToggleableState.Indeterminate
    }
    val selectionInteraction = remember { MutableInteractionSource() }
    val layoutInteraction = remember { MutableInteractionSource() }
    Row(Modifier.fillMaxWidth().height(GalleryDateHeaderHeight).background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
        Text(label, Modifier.weight(1f).semantics { heading() },
            style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1, overflow = TextOverflow.Ellipsis)
        if (selectedCount > 0) Text("$selectedCount/$count",
            style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        // Layout bounds end at the same inset as the date starts. Never shift hit areas
        // into the scrollbar rail to align the smaller glyph with the screen edge.
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            verticalAlignment = Alignment.CenterVertically) {
            GalleryHeaderControl(selectionInteraction,
                Modifier.triStateToggleable(selection, interactionSource = selectionInteraction,
                    indication = null, role = Role.Checkbox, onClick = onSelectGroup)
                    .semantics { contentDescription = if (selection == ToggleableState.On) deselectDescription else selectDescription }) {
                Icon(when (selection) {
                    ToggleableState.On -> Icons.Outlined.CheckCircle
                    ToggleableState.Indeterminate -> Icons.Outlined.RemoveCircleOutline
                    ToggleableState.Off -> Icons.Outlined.RadioButtonUnchecked
                }, null, Modifier.size(20.dp),
                    tint = if (selection == ToggleableState.Off) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.primary)
            }
            if (onLayout != null) {
                val description = stringResource(Res.string.gallery_layout)
                GalleryHeaderControl(layoutInteraction,
                    Modifier.clickable(interactionSource = layoutInteraction, indication = null,
                        role = Role.Button, onClick = onLayout).semantics { contentDescription = description }) {
                    Icon(Icons.Outlined.MoreVert, null, Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

/** A wide hit slot with a square visual surface keeps both actions and ripples concentric. */
@Composable
private fun GalleryHeaderControl(interaction: MutableInteractionSource, modifier: Modifier,
    content: @Composable () -> Unit) {
    Box(Modifier.width(WaifuSize.touchTarget).height(GalleryDateHeaderHeight).then(modifier),
        contentAlignment = Alignment.Center) {
        Box(Modifier.size(WaifuSize.overlayControl).clip(CircleShape)
            .indication(interaction, ripple(bounded = false, radius = WaifuSize.overlayControl / 2)),
            contentAlignment = Alignment.Center) {
            content()
        }
    }
}

/** One fixed slot owns the date and its actions throughout scrolling. */
@Composable
internal fun GalleryPinnedDateHeader(section: GallerySection, byMonth: Boolean, today: LocalDate,
    selectedIds: Set<Int>, onSelectGroup: () -> Unit, onLayout: () -> Unit) {
    Surface(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background, shadowElevation = 1.dp) {
        Box(Modifier.padding(horizontal = WaifuSpacing.content)) {
            GalleryDateHeader(
                label = if (byMonth) galleryMonthLabel(section.date, today) else galleryDateLabel(section.date, today),
                count = section.images.size, selectedCount = section.images.count { it.id in selectedIds },
                onSelectGroup = onSelectGroup, onLayout = onLayout)
        }
    }
}
