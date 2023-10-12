package com.markettwits.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Dehaze
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Preview
@Composable
private fun ImageScreenFullPreview() {
    ImageScreenFull(imageUrl = "")
}

@Composable
fun ImageScreenFull(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column {
            TopBarScreenImage()
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUrl,
                placeholder = painterResource(id = com.markettwits.core_ui.R.drawable.debug_image),
                contentScale = ContentScale.Fit,
                contentDescription = ""
            )
            DownBarScreenImage()
        }

    }
}

@Composable
fun TopBarScreenImage() {
    Row(modifier  = Modifier
        .padding(20.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween)
    {
        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "back", tint = MaterialTheme.colorScheme.onBackground)
        Icon(imageVector = Icons.Default.Dehaze, contentDescription = "menu",tint = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun DownBarScreenImage() {
    Row(modifier  = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween)
    {
        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "back",tint = MaterialTheme.colorScheme.onBackground)
        Icon(imageVector = Icons.Default.Dehaze, contentDescription = "menu",tint = MaterialTheme.colorScheme.onBackground)
    }
}