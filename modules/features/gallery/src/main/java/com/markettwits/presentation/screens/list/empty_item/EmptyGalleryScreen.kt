package com.markettwits.presentation.screens.list.empty_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.theme.FontRubik
import com.markettwits.core_ui.theme.WaifuPicsTheme
import com.markettwits.gallery.R

@Preview
@Composable
fun EmptyGalleryItemPreview() {
    WaifuPicsTheme {
        EmptyGalleryItem()
    }
}
@Composable
fun EmptyGalleryItem() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.gallery_empty_text),
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = FontRubik.medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        HeartIcon(modifier = Modifier.clickable { /* Handle click to add image */ })
    }
}

@Composable
fun HeartIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_heart),
        tint = MaterialTheme.colorScheme.onBackground,
        contentDescription = "Heart Icon",
        modifier = modifier
            .size(48.dp)
    )
}