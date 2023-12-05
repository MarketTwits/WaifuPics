package com.markettwits.presentation.screens.detail.info

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.components.Shapes
import com.markettwits.core_ui.di.ApplicationViewModel
import com.markettwits.core_ui.theme.FontRubik
import com.markettwits.core_ui.theme.LightPink
import com.markettwits.gallery.R
import com.markettwits.presentation.screens.detail.GalleryScreenViewModel
import com.markettwits.presentation.screens.detail.button.EditButton
import com.markettwits.presentation.screens.detail.button.OpenAsButton
import com.markettwits.presentation.screens.detail.button.SaveButton
import com.markettwits.presentation.screens.detail.button.ShareButton


@Composable
fun InfoImageContent(onDismiss: () -> Unit) {
    val viewModel: GalleryScreenViewModel.Base = ApplicationViewModel()
    val image = viewModel.infoAboutImage()
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(Shapes.medium)
            .wrapContentSize()
            .animateContentSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
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
                    viewModel.editImage()
                }, onClickOpenAs = {
                    viewModel.setImageAs()
                }, onClickShare = {
                    viewModel.shareImage()
                },
                onClickSave = {
                    viewModel.saveToGallery()
                    onDismiss()
                }
            )
            ImageInfoDate(image.created)
            ImageInfoColum(image.mediaInfo){
                viewModel.copy(it)
                Toast.makeText(context, context.getString(R.string.text_copied_to_clipboard), Toast.LENGTH_SHORT).show()
            }
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
            fontFamily = FontRubik.medium,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ImageInfoColum(mediaInfoList: List<MediaInfo>, onLongClick: (String) -> Unit) {
    mediaInfoList.forEach {
        MediaInfoRow(label = it.label, content = it.content, icon = it.icon, onLongClick = {
            onLongClick(it)
        })
    }
}

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
