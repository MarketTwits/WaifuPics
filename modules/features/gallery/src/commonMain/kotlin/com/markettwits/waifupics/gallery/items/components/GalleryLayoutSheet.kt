package com.markettwits.waifupics.gallery.items.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewQuilt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.design.WaifuActionButton
import com.markettwits.core_ui.components.design.WaifuSectionLabel
import com.markettwits.core_ui.components.design.WaifuSheet
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.gallery.items.model.GalleryLayout
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun GalleryLayoutSheet(current: GalleryLayout, onApply: (GalleryLayout) -> Unit, onDismiss: () -> Unit) {
    var custom by rememberSaveable { mutableStateOf(false) }
    var columns by rememberSaveable { mutableIntStateOf(current.columns.takeIf { it in 2..6 } ?: 3) }
    var byMonth by rememberSaveable { mutableStateOf(current.byMonth) }
    var keepAspectRatio by rememberSaveable { mutableStateOf(current.keepAspectRatio) }
    val day = current == GalleryLayout(3, false)
    val month = current == GalleryLayout(5, true)

    WaifuSheet(
        title = stringResource(if (custom) Res.string.gallery_layout_custom else Res.string.gallery_layout),
        onDismissRequest = onDismiss
    ) { dismiss ->
        Column(
            Modifier.fillMaxWidth().weight(1f, fill = false).verticalScroll(rememberScrollState())
                .padding(horizontal = WaifuSpacing.screen).padding(top = 16.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(WaifuSpacing.content)
        ) {
            fun apply(layout: GalleryLayout) { onApply(layout); dismiss() }
            if (!custom) {
                Surface(shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.surfaceContainer) {
                    Column(Modifier.fillMaxWidth()) {
                        LayoutChoice(stringResource(Res.string.gallery_layout_day), day, Icons.Outlined.GridView) {
                            apply(GalleryLayout(3, false))
                        }
                        LayoutChoice(stringResource(Res.string.gallery_layout_month), month, Icons.Outlined.GridOn) {
                            apply(GalleryLayout(5, true))
                        }
                        LayoutChoice(stringResource(Res.string.gallery_layout_proportions), current.keepAspectRatio,
                            Icons.Outlined.ViewQuilt, subtitle = stringResource(Res.string.gallery_layout_proportions_description)) {
                            apply(current.copy(keepAspectRatio = true))
                        }
                        LayoutChoice(stringResource(Res.string.gallery_layout_custom), !day && !month && !current.keepAspectRatio,
                            opensSettings = true) { custom = true }
                    }
                }
            } else {
                TextButton(onClick = { custom = false }) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, null, Modifier.size(20.dp))
                    Spacer(Modifier.width(WaifuSpacing.small))
                    Text(stringResource(Res.string.gallery_layout))
                }
                WaifuSectionLabel(stringResource(Res.string.gallery_image_layout))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                    FilterChip(!keepAspectRatio, { keepAspectRatio = false },
                        label = { Text(stringResource(Res.string.gallery_layout_grid)) }, modifier = Modifier.heightIn(min = 48.dp))
                    FilterChip(keepAspectRatio, { keepAspectRatio = true },
                        label = { Text(stringResource(Res.string.gallery_layout_proportions)) }, modifier = Modifier.heightIn(min = 48.dp))
                }
                WaifuSectionLabel(stringResource(if (keepAspectRatio) Res.string.gallery_density else Res.string.gallery_columns, columns))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                    (2..6).forEach { number ->
                        FilterChip(selected = number == columns, onClick = { columns = number },
                            label = { Text(number.toString()) }, modifier = Modifier.heightIn(min = 48.dp))
                    }
                }
                WaifuSectionLabel(stringResource(Res.string.gallery_grouping))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                    FilterChip(!byMonth, { byMonth = false }, label = { Text(stringResource(Res.string.gallery_layout_day)) },
                        modifier = Modifier.heightIn(min = 48.dp))
                    FilterChip(byMonth, { byMonth = true }, label = { Text(stringResource(Res.string.gallery_layout_month)) },
                        modifier = Modifier.heightIn(min = 48.dp))
                }
                WaifuActionButton(stringResource(Res.string.gallery_apply), { apply(GalleryLayout(columns, byMonth, keepAspectRatio)) },
                    modifier = Modifier.fillMaxWidth())
                TextButton(onClick = { apply(GalleryLayout()) }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(Res.string.gallery_layout_auto))
                }
            }
        }
    }
}

@Composable
private fun LayoutChoice(title: String, selected: Boolean, icon: ImageVector? = null,
    opensSettings: Boolean = false, subtitle: String? = null, onClick: () -> Unit) {
    val interaction = if (opensSettings) Modifier.clickable(onClick = onClick, role = Role.Button)
        else Modifier.selectable(selected, onClick = onClick, role = Role.RadioButton)
    Row(Modifier.fillMaxWidth().then(interaction).heightIn(min = 56.dp)
        .padding(horizontal = WaifuSpacing.content, vertical = WaifuSpacing.small),
        horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.content), verticalAlignment = Alignment.CenterVertically) {
        if (icon != null) Icon(icon, null, Modifier.size(20.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            if (subtitle != null) Text(subtitle, style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        if (selected) Icon(Icons.Default.Check, null, Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
        if (opensSettings) Icon(Icons.Outlined.ChevronRight, null, Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
