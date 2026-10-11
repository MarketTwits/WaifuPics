package com.markettwits.core_ui.components.design

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.markettwits.theme.components.WaifuSpacing

@Composable
fun WaifuSectionLabel(label: String, modifier: Modifier = Modifier) {
    Text(label, modifier.semantics { heading() }, style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant)
}

@Composable
fun WaifuInfoField(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small)) {
        WaifuSectionLabel(label)
        SelectionContainer { Text(value, style = MaterialTheme.typography.bodyLarge) }
    }
}

@Composable
fun WaifuTag(text: String) {
    Surface(shape = MaterialTheme.shapes.small, color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer) {
        Text(text, Modifier.padding(horizontal = WaifuSpacing.item, vertical = WaifuSpacing.small),
            style = MaterialTheme.typography.bodySmall)
    }
}
