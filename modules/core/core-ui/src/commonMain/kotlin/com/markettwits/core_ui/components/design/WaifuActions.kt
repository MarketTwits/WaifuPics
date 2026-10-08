package com.markettwits.core_ui.components.design

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.theme.components.WaifuSize

enum class WaifuActionStyle { Primary, Tonal, Outlined }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WaifuActionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    style: WaifuActionStyle = WaifuActionStyle.Tonal
) {
    val content: @Composable RowScope.() -> Unit = {
        if (icon != null) {
            Icon(icon, null, Modifier.size(WaifuSize.icon))
            Spacer(Modifier.width(WaifuSpacing.small))
        }
        Text(label, textAlign = TextAlign.Center)
    }
    val size = modifier.heightIn(min = WaifuSize.touchTarget)
    when (style) {
        WaifuActionStyle.Primary -> Button(onClick, shapes = ButtonDefaults.shapes(shape = MaterialTheme.shapes.medium, pressedShape = MaterialTheme.shapes.small), modifier = size,
            enabled = enabled, content = content)
        WaifuActionStyle.Tonal -> FilledTonalButton(onClick, shapes = ButtonDefaults.shapes(shape = MaterialTheme.shapes.medium, pressedShape = MaterialTheme.shapes.small), modifier = size,
            enabled = enabled, content = content)
        WaifuActionStyle.Outlined -> OutlinedButton(onClick, shapes = ButtonDefaults.shapes(shape = MaterialTheme.shapes.medium, pressedShape = MaterialTheme.shapes.small), modifier = size,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary),
            enabled = enabled, content = content)
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun WaifuOverlayButton(
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tint: Color = MaterialTheme.colorScheme.primary,
    content: @Composable () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val corner by animateDpAsState(
        if (pressed) WaifuSpacing.small else WaifuSize.overlayControl / 2,
        animationSpec = MaterialTheme.motionScheme.fastSpatialSpec(), label = "Overlay button shape")
    Surface(
        onClick = onClick, enabled = enabled, interactionSource = interaction,
        modifier = modifier.size(WaifuSize.touchTarget).semantics { contentDescription = description; role = Role.Button },
        shape = CircleShape, color = Color.Transparent,
        contentColor = tint.copy(alpha = if (enabled) 1f else 0.4f)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Surface(modifier = Modifier.size(WaifuSize.overlayControl),
                shape = RoundedCornerShape(corner),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
                contentColor = tint.copy(alpha = if (enabled) 1f else 0.4f)) {
                Box(contentAlignment = Alignment.Center) { content() }
            }
        }
    }
}

@Composable
fun WaifuActionTile(label: String, icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier,
    enabled: Boolean = true, destructive: Boolean = false, overlay: Boolean = false) {
    val foreground = if (overlay) Color.White else if (destructive) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.onSurface
    Surface(onClick = onClick, enabled = enabled,
        modifier = modifier.widthIn(min = 72.dp).heightIn(min = 60.dp),
        shape = MaterialTheme.shapes.medium,
        color = if (overlay) Color.Transparent else MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor = foreground.copy(alpha = if (enabled) 1f else 0.38f)) {
        Column(Modifier.padding(horizontal = WaifuSpacing.item, vertical = WaifuSpacing.small), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(WaifuSpacing.small, Alignment.CenterVertically)) {
            Icon(icon, null, Modifier.size(if (overlay) WaifuSize.overlayIcon else WaifuSize.icon))
            Text(label, style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
        }
    }
}
