package com.markettwits.waifupics.gallery.item.components.info

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markettwits.core_ui.components.design.WaifuSheet
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.Res
import waifupics.modules.features.gallery.generated.resources.image_information

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InfoImageBottomSheet(modifier: Modifier = Modifier, onDismiss: () -> Unit) {
    WaifuSheet(title = stringResource(Res.string.image_information), modifier = modifier,
        onDismissRequest = onDismiss) { dismissSheet ->
        InfoImageContent(onDismiss = dismissSheet)
    }
}
