package com.markettwits.waifupics.random.components.image_info.artist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.markettwits.core_ui.components.text.MultyLinksText
import com.markettwits.core_ui.components.text.UnlinkMultiText
import com.markettwits.theme.components.FontRubik
import com.markettwits.theme.components.Shapes
import com.markettwits.waifupics.random.model.AuthorUi

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
                .clip(Shapes.medium),
            model = imageUrl,
            contentDescription = name
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontFamily = FontRubik.medium()
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
                    .clip(Shapes.medium),
                model = iconUrl,
                contentDescription = userName
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = userName,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 14.sp,
                fontFamily = FontRubik.medium()
            )
        }
    }
}
