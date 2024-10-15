package com.markettwits.waifupics.navigation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DensityMedium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.image.DefaultImages

@Composable
fun TopBarPanel(
    modifier: Modifier = Modifier,
    isOpened : Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        MenuButton(isOpened = isOpened) { onClick() }
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            modifier = Modifier
                .scale(0.6f),
            painter = DefaultImages.favicon(),
            contentDescription = "icon"
        )
    }
}


@Composable
private fun MenuButton(
    modifier: Modifier = Modifier,
    isOpened: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .size(40.dp)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(7.dp)
    ) {
        Icon(
            imageVector = if (isOpened) Icons.Filled.Close else  Icons.Filled.DensityMedium,
            tint = MaterialTheme.colorScheme.surfaceTint,
            contentDescription = null
        )
    }
}