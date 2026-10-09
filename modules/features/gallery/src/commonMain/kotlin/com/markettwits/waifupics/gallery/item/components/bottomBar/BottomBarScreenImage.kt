package com.markettwits.waifupics.gallery.item.components.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.components.design.WaifuActionTile
import com.markettwits.theme.components.BlackScrim
import com.markettwits.theme.components.WaifuSpacing
import com.markettwits.waifupics.gallery.item.components.filmstrip.GalleryFilmstrip
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.*

@Composable
internal fun BoxScope.BottomBarScreenImage(showUI: Boolean, images: List<ImageFavoriteUi>, selectedIndex: Int,
    onPreview: (Int?) -> Unit, onSelect: (Int, Boolean) -> Unit, actionsEnabled: Boolean,
    onClickShareImage: () -> Unit, onClickDeleteImage: () -> Unit) {
    AnimatedVisibility(visible = showUI, modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
        enter = fadeIn(tween(220)) + slideInVertically(tween(220)) { it / 3 },
        exit = fadeOut(tween(140)) + slideOutVertically(tween(140)) { it / 3 }) {
        Column(Modifier.fillMaxWidth()
            .background(Brush.verticalGradient(listOf(Color.Transparent, BlackScrim)))
            .navigationBarsPadding().padding(top = WaifuSpacing.small, bottom = WaifuSpacing.small)) {
            GalleryFilmstrip(images, selectedIndex, onPreview, onSelect)
            Row(Modifier.fillMaxWidth().padding(horizontal = WaifuSpacing.screen),
                horizontalArrangement = Arrangement.spacedBy(WaifuSpacing.content, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically) {
                WaifuActionTile(stringResource(Res.string.share), Icons.Outlined.Share, onClickShareImage,
                    Modifier.weight(1f), enabled = actionsEnabled, overlay = true)
                WaifuActionTile(stringResource(Res.string.delete), Icons.Outlined.DeleteOutline, onClickDeleteImage,
                    Modifier.weight(1f), enabled = actionsEnabled, destructive = true, overlay = true)
            }
        }
    }
}
