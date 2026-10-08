package com.markettwits.waifupics.gallery.items.components.picker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.components.design.WaifuActionTile
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.theme.components.WaifuSize
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun SelectionSheet(modifier: Modifier = Modifier, selectedMedia: List<ImageFavoriteUi>,
    selectionState: Boolean, clearSelection: () -> Unit, shareImages: () -> Unit, deleteImages: () -> Unit) {
    val motion = MaterialTheme.motionScheme
    AnimatedVisibility(modifier = modifier, visible = selectionState,
        enter = slideInVertically(animationSpec = motion.defaultSpatialSpec()) { it },
        exit = slideOutVertically(animationSpec = motion.fastSpatialSpec()) { it }) {
        Surface(shape = MaterialTheme.shapes.extraLarge.copy(bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp)),
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface, shadowElevation = WaifuSpacing.small) {
            Column(Modifier.fillMaxWidth().navigationBarsPadding().padding(WaifuSpacing.item),
                verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(Res.string.selected_count, selectedMedia.size),
                        Modifier.weight(1f).padding(start = WaifuSpacing.small),
                        style = MaterialTheme.typography.titleMedium)
                    IconButton(onClick = clearSelection, modifier = Modifier.size(WaifuSize.touchTarget)) {
                        Icon(Icons.Outlined.Close, stringResource(Res.string.close), Modifier.size(WaifuSize.icon))
                    }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                    WaifuActionTile(stringResource(Res.string.share), Icons.Outlined.Share, shareImages,
                        Modifier.weight(1f), enabled = selectedMedia.isNotEmpty())
                    WaifuActionTile(stringResource(Res.string.delete), Icons.Outlined.DeleteOutline, deleteImages,
                        Modifier.weight(1f), enabled = selectedMedia.isNotEmpty(), destructive = true)
                }
            }
        }
    }
}
