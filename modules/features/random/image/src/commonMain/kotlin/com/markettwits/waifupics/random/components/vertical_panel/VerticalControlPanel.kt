package com.markettwits.waifupics.random.components.vertical_panel

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.waifupics.random.components.image_state.ImageState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.random.image.generated.resources.*

@Composable
fun VerticalControlPanel(
    modifier: Modifier = Modifier,
    imageState: ImageState,
    actionsReady: Boolean,
    showLabels: Boolean = true,
    isLoadingNext: Boolean = false,
    isFavorite: Boolean,
    imageId: Int?,
    onClickAddToFavorite: () -> Unit,
    onClickFetchRandomImage: () -> Unit,
    onClickShareImage: () -> Unit,
    onClickFilter: () -> Unit,
) {
    val loaded = imageState is ImageState.Success && actionsReady
    val heartScale = remember(imageId) { Animatable(1f) }
    var previousFavorite by remember(imageId) { mutableStateOf(isFavorite) }
    val haptic = LocalHapticFeedback.current
    LaunchedEffect(imageId, isFavorite) {
        val wasFavorite = previousFavorite
        previousFavorite = isFavorite
        if (isFavorite && !wasFavorite) {
            haptic.performHapticFeedback(HapticFeedbackType.ToggleOn)
            heartScale.animateTo(1.1f, tween(90))
            heartScale.animateTo(1f, spring(dampingRatio = 0.8f, stiffness = 500f))
        } else {
            if (wasFavorite && !isFavorite) haptic.performHapticFeedback(HapticFeedbackType.ToggleOff)
            heartScale.snapTo(1f)
        }
    }
    Column(
        modifier = modifier.width(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ActionButton(
            label = if (isFavorite) "Saved" else "Save",
            description = if (isFavorite) "Remove from favorites" else "Add to favorites",
            icon = painterResource(if (isFavorite) Res.drawable.ic_heart_solid else Res.drawable.ic_heart),
            enabled = loaded,
            showLabel = showLabels,
            tint = if (isFavorite) Color(0xFFF47185) else MaterialTheme.colorScheme.onBackground,
            iconScale = heartScale.value,
            onClick = onClickAddToFavorite
        )
        ActionButton("Share", "Share image", painterResource(Res.drawable.ic_share), loaded, showLabel = showLabels, onClick = onClickShareImage)
        ActionButton("Next", if (isLoadingNext) stringResource(Res.string.loading_more_images) else "Next image",
            painterResource(Res.drawable.ic_refresh), actionsReady && !isLoadingNext,
            showLabel = showLabels, isLoading = isLoadingNext, onClick = onClickFetchRandomImage)
        ActionButton("Filter", "Age rating filters", painterResource(Res.drawable.ic_filter), true, showLabel = showLabels, onClick = onClickFilter)
    }
}

@Composable
private fun ActionButton(
    label: String,
    description: String,
    icon: Painter,
    enabled: Boolean,
    showLabel: Boolean,
    isLoading: Boolean = false,
    iconScale: Float = 1f,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().clip(androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .semantics(mergeDescendants = true) { contentDescription = description },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier.size(36.dp).background(MaterialTheme.colorScheme.surface.copy(alpha = 0.94f), CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) CircularProgressIndicator(modifier = Modifier.size(18.dp), color = tint, strokeWidth = 2.dp)
                else Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp).graphicsLayer {
                    scaleX = iconScale
                    scaleY = iconScale
                }, tint = tint.copy(alpha = if (enabled) 1f else 0.4f))
            }
        }
        if (showLabel) Text(label, color = MaterialTheme.colorScheme.onBackground.copy(alpha = if (enabled) 1f else 0.5f), fontSize = 11.sp, lineHeight = 14.sp,
            fontFamily = com.markettwits.theme.components.FontRubik.medium(), maxLines = 1)
    }
}
