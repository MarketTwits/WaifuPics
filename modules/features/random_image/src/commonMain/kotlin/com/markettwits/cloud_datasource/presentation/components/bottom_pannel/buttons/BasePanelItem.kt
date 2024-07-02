package com.markettwits.cloud_datasource.presentation.components.bottom_pannel.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.Shapes

@Composable
fun BasePanelItem(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    image: Painter,
    contentDescription: String = "",
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .clip(Shapes.medium)
            .clickable(enabled = enabled) {
                onClick()
            }
            .background(if (!enabled) MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.secondary)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = image,
            tint = (if (!enabled) MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surfaceTint),
            contentDescription = contentDescription
        )
    }
}
