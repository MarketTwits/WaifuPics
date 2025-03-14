package com.markettwits.waifupics.gallery.items.components.picker

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter

@Composable
internal fun CheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheck: (() -> Unit)? = null
) {
    val image = if (isChecked) Icons.Filled.CheckCircle else Icons.Outlined.Circle
    val color = if (isChecked) MaterialTheme.colorScheme.surfaceTint
    else MaterialTheme.colorScheme.onSurface
    if (onCheck != null) {
        IconButton(
            onClick = onCheck,
            modifier = modifier
        ) {
            Image(
                imageVector = image,
                colorFilter = ColorFilter.tint(color),
                contentDescription = null
            )
        }
    } else {
        Image(
            imageVector = image,
            colorFilter = ColorFilter.tint(color),
            modifier = modifier,
            contentDescription = null
        )
    }
}