package com.markettwits.waifupics.random.components.image_state.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.image.DefaultImages
import com.markettwits.waifupics.random.components.image_state.loading.components.RoundedLinearProgressIndicator


@Composable
internal fun ImageLoading(modifier: Modifier = Modifier) {
    val scaleFactor = 0.35f // Adjust this value to control the size reduction
    Box(
        modifier = modifier
            .padding(20.dp)
            .height(350.dp)
            .fillMaxWidth()
            .clip(com.markettwits.theme.components.Shapes.medium)
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scaleFactor)
        ) {
            Icon(
                painter = DefaultImages.favicon(),
                contentDescription = null,
                tint = com.markettwits.theme.components.Pink
            )
            Spacer(modifier = Modifier.height(10.dp))
            RoundedLinearProgressIndicator()
        }
    }
}