package com.markettwits.random_image.presentation.image.fuckup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.R
import com.markettwits.core_ui.theame.theme.WaifuPicsTheme

@Composable
fun ImageFuckup(message : String) {
    val scaleFactor = 0.6f
    Box(
        modifier = Modifier
            .padding(30.dp)
            .height(350.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .scale(scaleFactor)
        ) {
            Image(
                modifier = Modifier
                    .height(200.dp)
                .aspectRatio(1f),
                painter = painterResource(id = R.drawable.senpai),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Something wrong",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = FontFamily(
                    Font(R.font.rubik_medium),
                )
            )
            Spacer(modifier = Modifier.fillMaxWidth().padding(5.dp))
            Text(
                text = message,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.rubik_regular))
            )
        }

    }
}

@Composable
@Preview
private fun ImageFuckupPreview() {
    WaifuPicsTheme() {
        ImageFuckup("Unable to resolve host \"api.nekosapi.com\": No address associated with hostname")
    }
}
@Composable
@Preview
private fun ImageFuckupPreviewDark() {
    WaifuPicsTheme(darkTheme = true) {
        ImageFuckup("Unable to resolve host \"api.nekosapi.com\": No address associated with hostname")
    }
}