package com.markettwits.waifupics.view.main.image_info.artist.success

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.waifupics.R
import com.markettwits.waifupics.ui.theme.LightGrey
import com.markettwits.waifupics.view.base.ImageParametersText
import com.markettwits.waifupics.view.base.MultiImageParametersText

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
@Preview
@Composable
fun ArtistInfo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column {
            ArtistTitle()
            ImageParametersText(
                title = "Aliases",
                content = "夕末",
                link = "www.google.com"
            )
            MultiImageParametersText(
                title = "Links",
                content = listOf(
                    "\uD83D\uDD17 Pixiv",
                    "\uD83D\uDD17 Twitter",
                    "\uD83D\uDD17 Weibu"
                ),
                link = listOf(
                    "https://www.pixiv.net/en/users/19389056",
                    "https://twitter.com/xilmo1",
                    "https://weibo.com/3541932414"
                )
            )

        }
    }
}
@Composable
fun ArtistTitle() {
    val user = "User"
    val iconProfile =
        "https://pbs.twimg.com/profile_images/1563823251293229056/CHZ_lqn3_400x400.jpg"
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(20.dp)),
            painter = painterResource(id = R.drawable.artist_profile_test),
            contentDescription = user
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "xilmo",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.rubik_medium))
        )
    }
}
