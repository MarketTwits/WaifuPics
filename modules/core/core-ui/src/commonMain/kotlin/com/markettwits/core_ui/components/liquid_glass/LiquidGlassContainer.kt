package com.markettwits.core_ui.components.liquid_glass

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LiquidGlassContainer(
    modifier: Modifier = Modifier,
    blurRadius: Dp = 16.dp,
    backgroundAlpha: Float = 0.2f,
    tintColor: Color = Color.White,
    cornerRadius: Dp = 24.dp,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        // Blurred background layer for depth
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            tintColor.copy(alpha = backgroundAlpha * 1.3f),
                            tintColor.copy(alpha = backgroundAlpha * 1.0f)
                        )
                    )
                )
                .blur(radius = blurRadius)
        )

        // Frosted overlay for opacity
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    tintColor.copy(alpha = backgroundAlpha * 0.6f)
                )
        )

        content()
    }
}
