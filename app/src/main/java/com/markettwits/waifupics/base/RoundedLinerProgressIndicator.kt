package com.markettwits.waifupics.base

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.markettwits.waifupics.theame.theme.Pink

@Composable fun RoundedLinearProgressIndicator(
    modifier: Modifier = Modifier,
    height: Dp = 8.dp,
    color: Color = Pink,
    backgroundColor: Color = color.copy(alpha = 0.1f)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedColor by infiniteTransition.animateColor(
        initialValue = color,
        targetValue = color.copy(alpha = .5f),
        animationSpec = infiniteRepeatable(
            animation = tween(768, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val animTranslationX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = .75f,
        animationSpec = infiniteRepeatable(
            animation = tween(1024, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(height)
                .padding(start = 32.dp, end = 32.dp)
                .background(backgroundColor, CircleShape)
        ) {
            BoxWithConstraints {
                Box(
                    Modifier
                        .graphicsLayer {
                            translationX = (maxWidth * animTranslationX).toPx()
                        }
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .width(maxWidth * .25f)
                        .background(animatedColor, CircleShape)
                )
            }
        }
    }
}