package com.markettwits.waifupics.random.components.bottom_info

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.design.WaifuInfoField
import com.markettwits.core_ui.components.design.WaifuImageMetadataSections
import com.markettwits.core_ui.components.design.WaifuSheet
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.random.components.image_state.ImageState
import com.markettwits.waifupics.random.model.RandomImageState
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun ImageInformationSheet(image: RandomImageState.Success, loadedImage: ImageState.Success?, onDismiss: () -> Unit) {
    val clipboard = LocalClipboard.current
    WaifuSheet(title = stringResource(Res.string.image_information), onDismissRequest = onDismiss) {
        Column(Modifier.fillMaxWidth().weight(1f, fill = false).verticalScroll(rememberScrollState())
            .padding(top = 16.dp, bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(WaifuSpacing.section)) {
            WaifuInfoField(stringResource(Res.string.image_size),
                loadedImage?.let { "${it.width} × ${it.height}" } ?: stringResource(Res.string.image_size_pending),
                Modifier.padding(horizontal = WaifuSpacing.screen))
            WaifuImageMetadataSections(image.id, image.imageData.artistName, image.imageData.ageRating,
                image.imageData.sourceUrl, image.imageData.tags, image.colorPalette,
                onCopy = { text ->
                    checkNotNull(clipboard.nativeClipboard)
                    clipboard.setClipEntry(plainTextClipEntry(text))
                })
        }
    }
}
