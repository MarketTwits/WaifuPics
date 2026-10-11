package com.markettwits.waifupics.gallery.item.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.Res
import waifupics.modules.features.gallery.generated.resources.delete
import waifupics.modules.features.gallery.generated.resources.save_to_gallery
import waifupics.modules.features.gallery.generated.resources.share
import waifupics.modules.features.gallery.generated.resources.use_as

@Composable
internal fun ShareButton(
    overlay: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Outlined.Share,
        overlay = overlay,
        title = stringResource(Res.string.share)
    ) {
        onItemClick()
    }
}

@Composable
internal fun OpenAsButton(
    overlay: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
        overlay = overlay,
        title = stringResource(Res.string.use_as)
    ) {
        onItemClick()
    }
}

@Composable
internal fun SaveButton(
    overlay: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Default.Save,
        overlay = overlay,
        title = stringResource(Res.string.save_to_gallery)
    ) {
        onItemClick()
    }
}

@Composable
internal fun DeleteButton(
    overlay: Boolean = false,
    onItemClick: () -> Unit
) {
    BottomBarColumn(
        imageVector = Icons.Outlined.DeleteOutline,
        overlay = overlay,
        title = stringResource(Res.string.delete)
    ) {
        onItemClick()
    }
}

