package com.markettwits.presentation.detail.info

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.presentation.detail.button.EditButton
import com.markettwits.presentation.detail.button.OpenAsButton
import com.markettwits.presentation.detail.button.ShareButton
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme

@Composable
@Preview
private fun InfoImageContentPreviewLight() {
    WaifuPicsTheme {
        InfoImageContent()
    }
}

@Composable
@Preview
private fun InfoImageContentPreviewDark() {
    WaifuPicsTheme(darkTheme = true) {
        InfoImageContent()
    }
}

@Composable
fun InfoImageContent() {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
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
            MediaViewInfoActions()
            ImageInfoDate("ноября 5, 2023 4:09 PM")
            ImageInfoColum(mediaInfo())

        }
    }
}

@Composable
private fun ImageInfoDate(date: String) {
    Box(
        modifier = Modifier
            .clip(CircleShape.copy(CornerSize(10.dp)))
    ) {
        Text(
            text = date,
            color = MaterialTheme.colorScheme.surfaceTint,
            fontFamily = FontFamily(Font(com.markettwits.core_ui.R.font.rubik_medium)),
            fontSize = 14.sp
        )
    }
}
@Composable
private fun ImageInfoColum(mediaInfoList: List<MediaInfo>){
    Column() {
        mediaInfoList.forEach {
            MediaInfoRow(label = it.label, content = it.content, icon = it.icon)
        }
    }
}
@Composable
private fun MediaViewInfoActions(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        // Share Component
        ShareButton(followTheme = true)
        // Use as or Open With
        OpenAsButton(followTheme = true)
        // Edit
        EditButton(followTheme = true)
    }
}

fun mediaInfo() = listOf(
    MediaInfo(1, "Label", "Screenshot_202310105-2030230.jpg",Icons.Default.AddPhotoAlternate),
    MediaInfo(2, "Metadata", "261,01KM 2,4 MP 1080 * 2280", Icons.Default.MarkEmailRead),
    MediaInfo(3, "Path", "/storage/emulated/0/Pictures/Screenshots",Icons.Default.PhotoAlbum),
)

