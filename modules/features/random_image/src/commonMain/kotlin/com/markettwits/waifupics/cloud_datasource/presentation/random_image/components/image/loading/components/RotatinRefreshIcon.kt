package com.markettwits.waifupics.cloud_datasource.presentation.random_image.components.image.loading.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import waifupics.modules.features.random_image.generated.resources.Res
import waifupics.modules.features.random_image.generated.resources.ic_refresh


@Composable
fun RotationRefreshIcon(
    isLoading: Boolean,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotatingValue = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Icon(
        modifier = Modifier
            .size(20.dp)
            .rotate(if (isLoading)rotatingValue.value else 0f),
        painter = org.jetbrains.compose.resources.painterResource(Res.drawable.ic_refresh),
        tint = MaterialTheme.colorScheme.surfaceTint,
        contentDescription = "Fetch new image"
    )
}