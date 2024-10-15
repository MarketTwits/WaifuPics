package com.markettwits.waifupics.gallery.items.components.picker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.theme.Shapes
import com.markettwits.waifupics.gallery.items.model.ImageFavoriteUi
import com.markettwits.waifupics.gallery.item.components.button.DeleteButton
import com.markettwits.waifupics.gallery.item.components.button.ShareButton
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.Res
import waifupics.modules.features.gallery.generated.resources.close
import waifupics.modules.features.gallery.generated.resources.selected_count


@Composable
fun SelectionSheet(
    modifier: Modifier = Modifier,
    selectedMedia: List<ImageFavoriteUi>,
    selectionState: Boolean,
    clearSelection : () -> Unit,
    shareImages : () -> Unit,
    deleteImages : () -> Unit
) {
    val sizeModifier = Modifier.fillMaxWidth()

    AnimatedVisibility(
        modifier = modifier,
        visible = selectionState,
        enter = slideInVertically { it * 2 },
        exit = slideOutVertically { it * 2 }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .then(sizeModifier)
                .wrapContentHeight()
                .clip(Shapes.medium)
                .shadow(
                    elevation = 4.dp,
                    shape = Shapes.medium
                )
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .then(sizeModifier)
                    .height(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(
                    onClick = { clearSelection() },
                    modifier = Modifier.size(24.dp),
                ) {
                    Image(
                        imageVector = Icons.Outlined.Close,
                        colorFilter = ColorFilter.tint(LocalContentColor.current),
                        contentDescription = stringResource(Res.string.close)
                    )
                }
                Text(
                    text = stringResource(Res.string.selected_count, selectedMedia.size),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }
            Row(
                modifier = Modifier
                    .then(sizeModifier)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = Shapes.medium
                    )
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ShareButton {
                    shareImages()
                }

                DeleteButton {
                    deleteImages()
                }
            }
        }
    }
}
