package com.markettwits.waifupics.gallery.item.components.info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.markettwits.core_ui.components.design.*
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.gallery.item.viewmodel.GalleryScreenViewModel
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*

@Suppress("DEPRECATION")
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ColumnScope.InfoImageContent(modifier: Modifier = Modifier, onDismiss: () -> Unit) {
    val viewModel: GalleryScreenViewModel = ApplicationViewModel()
    val image by viewModel.currentImage().collectAsState()
    val clipboard = LocalClipboardManager.current
    val actionsPerRow = if (LocalDensity.current.fontScale > 1.3f) 1 else 2
    Column(modifier.fillMaxWidth().weight(1f, fill = false).verticalScroll(rememberScrollState())
        .padding(top = 16.dp, bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.section)) {
        FlowRow(Modifier.fillMaxWidth().padding(horizontal = WaifuSpacing.screen), maxItemsInEachRow = actionsPerRow,
            horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.screen),
            verticalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
            WaifuInfoField(stringResource(Res.string.saved_on), image.created, Modifier.weight(1f))
            if (image.width > 0 && image.height > 0) {
                WaifuInfoField(stringResource(Res.string.image_size), "${image.width} × ${image.height}", Modifier.weight(1f))
            }
        }
        val metadata = image.metadata
        if (metadata != null) {
            WaifuImageMetadataSections(image.id, metadata.artistName, metadata.ageRating, metadata.sourceUrl,
                metadata.tags, metadata.colorPalette, onCopy = { clipboard.setText(AnnotatedString(it)) })
        } else {
            Surface(Modifier.padding(horizontal = WaifuSpacing.screen), shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceContainerHigh, contentColor = MaterialTheme.colorScheme.onSurfaceVariant) {
                Row(Modifier.fillMaxWidth().padding(WaifuSpacing.content), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
                    Icon(Icons.Outlined.Info, null, Modifier.size(20.dp))
                    Text(stringResource(Res.string.gallery_metadata_not_saved), Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        Column(Modifier.fillMaxWidth().padding(horizontal = WaifuSpacing.screen),
            verticalArrangement = Arrangement.spacedBy(WaifuSpacing.item)) {
            WaifuSectionLabel(stringResource(Res.string.image_actions))
            FlowRow(Modifier.fillMaxWidth(),
                maxItemsInEachRow = actionsPerRow,
                horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.item),
                verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                WaifuActionButton(stringResource(Res.string.share), viewModel::onClickShareImage,
                    modifier = Modifier.weight(1f), icon = Icons.Outlined.Share)
                WaifuActionButton(stringResource(Res.string.use_as), viewModel::onClickSetImageAs,
                    modifier = Modifier.weight(1f), icon = Icons.AutoMirrored.Filled.OpenInNew)
            }
            WaifuActionButton(stringResource(Res.string.save_to_gallery), {
                viewModel.onClickSaveToGallery()
                onDismiss()
            }, modifier = Modifier.fillMaxWidth(), icon = Icons.Default.Save,
                style = WaifuActionStyle.Outlined)
        }
    }
}
