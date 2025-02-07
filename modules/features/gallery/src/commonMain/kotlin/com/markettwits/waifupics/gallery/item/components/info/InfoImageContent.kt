package com.markettwits.waifupics.gallery.item.components.info

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.theme.components.FontRubik
import com.markettwits.theme.components.LightPink
import com.markettwits.theme.components.Shapes
import com.markettwits.waifupics.gallery.item.components.button.OpenAsButton
import com.markettwits.waifupics.gallery.item.components.button.SaveButton
import com.markettwits.waifupics.gallery.item.components.button.ShareButton
import com.markettwits.waifupics.gallery.item.viewmodel.GalleryScreenViewModel


@Composable
internal fun InfoImageContent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val viewModel: GalleryScreenViewModel = ApplicationViewModel()
    val image by viewModel.currentImage().collectAsState()
    Box(
        modifier = modifier
            .padding(10.dp)
            .clip(Shapes.medium)
            .animateContentSize(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            MediaViewInfoActions(
                onClickOpenAs = {
                    viewModel.onClickSetImageAs()
                }, onClickShare = {
                    viewModel.onClickShareImage()
                },
                onClickSave = {
                    viewModel.onClickSaveToGallery()
                    onDismiss()
                }
            )
            ImageInfoDate(image.created)
        }
    }
}

@Composable
private fun ImageInfoDate(date: String) {
    Box(
        modifier = Modifier.clip(Shapes.medium)
    ) {
        Text(
            text = date,
            color = LightPink,
            fontFamily = FontRubik.medium(),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun MediaViewInfoActions(
    onClickShare: () -> Unit,
    onClickOpenAs: () -> Unit,
    onClickSave: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ShareButton {
            onClickShare()
        }
        OpenAsButton {
            onClickOpenAs()
        }
        SaveButton {
            onClickSave()
        }
    }
}
