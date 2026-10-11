package com.markettwits.core_ui.components.design

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.WaifuSpacing

@Composable
fun WaifuDialog(title: String, message: String, confirmLabel: String, dismissLabel: String,
    onConfirm: () -> Unit, onDismiss: () -> Unit, loading: Boolean = false) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = MaterialTheme.shapes.extraLarge,
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(WaifuSpacing.content)) {
                if (message.isNotBlank()) Text(message)
                if (loading) WaifuLoadingIndicator()
            }
        },
        confirmButton = { WaifuActionButton(confirmLabel, onConfirm, enabled = !loading) },
        dismissButton = { TextButton(onClick = onDismiss,
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)) { Text(dismissLabel) } }
    )
}

@Composable
fun WaifuLoadingIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier.size(40.dp), color = MaterialTheme.colorScheme.primary)
}
