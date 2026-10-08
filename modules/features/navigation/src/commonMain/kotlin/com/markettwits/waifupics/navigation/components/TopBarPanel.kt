package com.markettwits.waifupics.navigation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DensityMedium
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.image.DefaultImages

@Composable
fun TopBarPanel(
    modifier: Modifier = Modifier,
    isOpened: Boolean,
    overlay: Boolean = false,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .background(if (overlay) Color.Transparent else MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(56.dp).padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick) {
            Box(
                Modifier.size(36.dp).background(
                    if (overlay) MaterialTheme.colorScheme.surface.copy(alpha = 0.94f) else Color.Transparent,
                    CircleShape
                ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isOpened) Icons.Filled.Close else Icons.Filled.DensityMedium,
                    modifier = Modifier.size(if (overlay) 20.dp else 24.dp),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = if (isOpened) "Close menu" else "Open menu"
                )
            }
        }
        Image(DefaultImages.favicon(), contentDescription = null, modifier = Modifier.size(24.dp))
    }
}
