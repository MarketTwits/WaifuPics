package com.markettwits.waifupics.view.main.ui.image.fuckup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.waifupics.R
import com.markettwits.waifupics.theame.theme.WaifuPicsTheme

@Composable
fun ImageFuckup() {
    val errorMessage = "Remote server don't response"
    val scaleFactor = 0.6f
    Box(
        modifier = Modifier
            .padding(30.dp)
            .height(350.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scaleFactor)
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.senpai),
                contentDescription = null,
            )
            Text(
                text = "Something wrnog",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily(
                    Font(R.font.rubik_medium),
                )
            )
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.rubik_regular))
            )
        }
    }
}

@Composable
@Preview
private fun ImageFuckupPreview() {
    WaifuPicsTheme() {
        ImageFuckup()
    }
}