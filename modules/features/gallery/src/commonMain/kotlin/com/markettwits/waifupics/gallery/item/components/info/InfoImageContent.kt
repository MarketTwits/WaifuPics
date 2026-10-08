package com.markettwits.waifupics.gallery.item.components.info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.markettwits.core_ui.components.design.*
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.gallery.item.viewmodel.GalleryScreenViewModel
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun InfoImageContent(modifier: Modifier = Modifier, onDismiss: () -> Unit) {
    val viewModel: GalleryScreenViewModel = ApplicationViewModel()
    val image by viewModel.currentImage().collectAsState()
    val actionsPerRow = if (LocalDensity.current.fontScale > 1.3f) 1 else 2
    Column(modifier.fillMaxWidth().verticalScroll(rememberScrollState())
        .padding(WaifuSpacing.screen), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.section)) {
        WaifuInfoField(stringResource(Res.string.saved_on), image.created)
        if (image.width > 0 && image.height > 0) {
            WaifuInfoField(stringResource(Res.string.image_size), "${image.width} × ${image.height}")
        }
        Column(Modifier.fillMaxWidth(),
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
