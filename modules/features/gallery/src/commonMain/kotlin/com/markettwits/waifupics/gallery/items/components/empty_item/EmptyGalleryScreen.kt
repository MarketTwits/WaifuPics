package com.markettwits.waifupics.gallery.items.components.empty_item

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import waifupics.modules.features.gallery.generated.resources.Res
import waifupics.modules.features.gallery.generated.resources.gallery_empty_text
import waifupics.modules.features.gallery.generated.resources.ic_heart

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
            text = stringResource(Res.string.gallery_empty_text),
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = com.markettwits.theme.components.FontRubik.medium(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        HeartIcon(modifier = Modifier.clickable { /* Handle click to add image */ })
    }
}

@Composable
fun HeartIcon(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(Res.drawable.ic_heart),
        tint = MaterialTheme.colorScheme.onBackground,
        contentDescription = "Heart Icon",
        modifier = modifier
            .size(48.dp)
    )
}