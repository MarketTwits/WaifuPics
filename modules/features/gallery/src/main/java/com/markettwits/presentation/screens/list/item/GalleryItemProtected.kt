package com.markettwits.presentation.screens.list.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.R
import run.nabla.gallerypicker.picker.GalleryPickerState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryItemProtected(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    state: GalleryPickerState,
) {
    val shape = RoundedCornerShape(10.dp)
    Card(
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = shape)
            .padding(2.dp),
        onClick = { }
    ) {
        Box(modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .heightIn(min = state.itemMinHeight.dp, max = state.itemMaxHeight.dp)
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = R.drawable.ic_warning),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }

    }
}