package com.markettwits.random_image.presentation.random_image_screen.image_info.artist.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
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
import coil.compose.AsyncImage
import com.markettwits.core_ui.R
import com.markettwits.core_ui.components.MultyLinksText
import com.markettwits.core_ui.components.UnlinkMultiText
import com.markettwits.random_image.presentation.random_image_screen.AuthorUi
import com.markettwits.core_ui.theme.LightGrey

@Preview
@Composable
fun ArtistEmptyInfo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Filled.Close,
                tint = LightGrey,
                contentDescription = null
            )
            Text(
                text = "No artist",
                color = LightGrey,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.rubik_regular))
            )
        }
    }
}

@Composable
fun ArtistInfo(author : AuthorUi) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            ArtistTitle(author.imageUrl, author.title)
            UnlinkMultiText(
                title = "Aliases: ",
                text = author.aliases
            )
            MultyLinksText(
                title = "Links: ",
                links = author.links
            )
        }
    }
}

@Composable
fun ArtistTitle(imageUrl : String, name : String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(20.dp)),
            model = imageUrl,
            contentDescription = name
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.rubik_medium))
        )
    }
}

@Composable
fun Uploader(userName: String, iconUrl: String) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(20.dp)),
                model = iconUrl,
                contentDescription = userName
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = userName,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.rubik_medium))
            )
        }
    }
}
