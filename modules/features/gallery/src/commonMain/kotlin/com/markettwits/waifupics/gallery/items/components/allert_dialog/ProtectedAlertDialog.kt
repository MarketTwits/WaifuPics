package com.markettwits.waifupics.gallery.items.components.allert_dialog

import androidx.compose.runtime.Composable
import com.markettwits.core_ui.components.design.WaifuDialog

@Composable
fun ProtectedAlertDialog(onSubmit: () -> Unit, onDismiss: () -> Unit) {
    WaifuDialog(title = "Get updates",
        message = "Allow notifications when new art styles are added.",
        confirmLabel = "Allow", dismissLabel = "Not now", onConfirm = onSubmit, onDismiss = onDismiss)
}
