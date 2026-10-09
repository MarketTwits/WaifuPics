package com.markettwits.waifupics.gallery.item.components.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.markettwits.theme.components.BlackScrim
import com.markettwits.waifupics.gallery.item.components.info.InfoImageBottomSheet
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*


@Composable
internal fun BoxScope.TopBarScreenImage(
    showUI: Boolean,
    createdDate : String,
    position: Int,
    total: Int,
    informationEnabled: Boolean = true,
    onClickGoBack : () -> Unit
) {
    var infoImage by remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        visible = showUI,
        enter = fadeIn(tween(220)) + slideInVertically(tween(220)) { -it / 3 },
        exit = fadeOut(tween(140)) + slideOutVertically(tween(140)) { -it / 3 },
    ) {
        Row(
            Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(BlackScrim, Color.Transparent)
                    )
                )
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                .padding(start = 5.dp, end = 8.dp)
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onClickGoBack) {
                Icon(Icons.Default.ArrowBackIosNew, "Back", Modifier.size(22.dp), tint = Color.White)
            }
            Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(createdDate, color = Color.White, style = MaterialTheme.typography.titleMedium)
                Text(stringResource(Res.string.gallery_viewer_position, position, total),
                    color = Color.White.copy(alpha = 0.75f), style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { infoImage = !infoImage }, enabled = informationEnabled) {
                Icon(Icons.Default.MoreVert, stringResource(Res.string.image_information), Modifier.size(22.dp),
                    tint = Color.White.copy(alpha = if (informationEnabled) 1f else 0.4f))
            }
            if (infoImage) {
                InfoImageBottomSheet {
                    infoImage = false
                }
            }
        }
    }
}
