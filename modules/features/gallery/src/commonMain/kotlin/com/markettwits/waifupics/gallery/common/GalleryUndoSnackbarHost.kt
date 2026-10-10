package com.markettwits.waifupics.gallery.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.WaifuSize
import com.markettwits.theme.components.WaifuSpacing
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import waifupics.modules.features.gallery.generated.resources.*

/** Keep one host outside navigation: deleting the last photo must still offer undo. */
@Composable
fun GalleryUndoSnackbarHost(modifier: Modifier = Modifier) {
    val deletion = koinInject<GalleryDeletion>()
    val notice by deletion.notice.collectAsState()
    val host = remember { SnackbarHostState() }
    val message = notice?.let { value -> stringResource(when (value.error) {
        GalleryDeletionError.Delete -> Res.string.gallery_delete_failed
        GalleryDeletionError.Restore -> Res.string.gallery_restore_failed
        null -> if (value.count == 1) Res.string.gallery_deleted else Res.string.gallery_deleted_count
    }, value.count) }.orEmpty()
    val undo = stringResource(Res.string.gallery_undo)
    // A new batch cancels the old message and starts a fresh accessible undo window.
    LaunchedEffect(notice?.token, message, undo) {
        val value = notice ?: return@LaunchedEffect
        when (host.showSnackbar(message, actionLabel = undo.takeIf { value.count > 0 },
            withDismissAction = true, duration = SnackbarDuration.Long)) {
            SnackbarResult.ActionPerformed -> deletion.undo(value.token)
            SnackbarResult.Dismissed -> deletion.dismiss(value.token)
        }
    }
    SnackbarHost(host, modifier.widthIn(max = 560.dp).fillMaxWidth()) { data ->
        Surface(shape = MaterialTheme.shapes.large, color = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface, shadowElevation = 4.dp) {
            Snackbar(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurface,
                action = data.visuals.actionLabel?.let { label -> {
                    TextButton(onClick = data::performAction, modifier = Modifier.heightIn(min = WaifuSize.touchTarget)) {
                        Text(label, style = MaterialTheme.typography.labelLarge)
                    }
                } }, dismissAction = {
                    IconButton(onClick = data::dismiss, modifier = Modifier.size(WaifuSize.touchTarget)) {
                        Icon(Icons.Outlined.Close, stringResource(Res.string.close), Modifier.size(20.dp))
                    }
                }) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
                    if (notice?.error == null) Icon(Icons.Outlined.CheckCircleOutline, null,
                        Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
                    Text(data.visuals.message, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
