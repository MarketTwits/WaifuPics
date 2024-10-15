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
import com.markettwits.core_ui.theme.Shapes
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.core_ui.theme.FontRubik
import com.markettwits.core_ui.theme.LightPink
import com.markettwits.waifupics.gallery.item.viewmodel.GalleryScreenViewModel
import com.markettwits.waifupics.gallery.item.components.button.EditButton
import com.markettwits.waifupics.gallery.item.components.button.OpenAsButton
import com.markettwits.waifupics.gallery.item.components.button.SaveButton
import com.markettwits.waifupics.gallery.item.components.button.ShareButton


@Composable
fun InfoImageContent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val viewModel: GalleryScreenViewModel = ApplicationViewModel()
    val image by viewModel.currentImage().collectAsState()
    Box(
        modifier = modifier
            .padding(10.dp)
//            .padding(
//                bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
//            )
            .clip(Shapes.medium)
//            .wrapContentSize()
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
                onClickEdit = {
                    viewModel.onClickEditImage()
                }, onClickOpenAs = {
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

//@Composable
//private fun ImageInfoColum(mediaInfoList: List<MediaInfo>, onLongClick: (String) -> Unit) {
//    mediaInfoList.forEach {
//        MediaInfoRow(label = it.label, content = it.content, icon = it.icon, onLongClick = {
//            onLongClick(it)
//        })
//    }
//}

@Composable
private fun MediaViewInfoActions(
    onClickShare: () -> Unit,
    onClickOpenAs: () -> Unit,
    onClickEdit: () -> Unit,
    onClickSave: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ShareButton() {
            onClickShare()
        }
        OpenAsButton() {
            onClickOpenAs()
        }
        EditButton() {
            onClickEdit()
        }
        SaveButton {
            onClickSave()
        }
    }
}
