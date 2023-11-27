package com.markettwits.presentation.screens.list.picker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.markettwits.core_ui.image.LocalImageLoader
import com.markettwits.presentation.animations.Animation
import com.markettwits.presentation.screens.ImageFavoriteUi
import run.nabla.gallerypicker.picker.GalleryPickerState


@Composable
fun MediaImage(
    image: ImageFavoriteUi,
    imagePickerState : GalleryPickerState,
    //selectionState: MutableState<Boolean>,
    selectionState: Boolean,
    selectedMedia: List<ImageFavoriteUi>,
    isSelected: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    if (!selectionState) {
        isSelected.value = false
    } else {
        isSelected.value = selectedMedia.find { it.id == image.id } != null
    }
    val selectedSize by animateDpAsState(
        if (isSelected.value) 12.dp else 0.dp, label = "selectedSize"
    )
    val selectedShapeSize by animateDpAsState(
        if (isSelected.value) 16.dp else 0.dp, label = "selectedShapeSize"
    )
    val strokeSize by animateDpAsState(
        targetValue = if (isSelected.value) 2.dp else 0.dp, label = "strokeSize"
    )
    val strokeColor by animateColorAsState(
        targetValue = if (isSelected.value) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
        label = "strokeColor"
    )
    Box(
        modifier = modifier
            .heightIn(min = imagePickerState.itemMinHeight.dp, max = imagePickerState.itemMaxHeight.dp)
           // .aspectRatio(1f)
            //.size(Dimens.Photo())
    ) {
            val shape = RoundedCornerShape(10.dp)
            Card(
                shape = shape,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(selectedSize)
                    .clip(RoundedCornerShape(selectedShapeSize))
                    //.background(
                       // color = MaterialTheme.colorScheme.surfaceContainerHigh,
                      //  shape = RoundedCornerShape(selectedShapeSize)
                   // )
                    .border(
                        width = strokeSize,
                        shape = RoundedCornerShape(selectedShapeSize),
                        color = strokeColor
                    )
                    .padding(2.dp),
                onClick = { }
            ) {
                AsyncImage(
                    modifier = modifier
                        .fillMaxSize(),
                    model = LocalImageLoader.current.single(image.imageUrl),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        AnimatedVisibility(
            visible = selectionState,
            enter = Animation.enterAnimation,
            exit = Animation.exitAnimation
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                CheckBox(isChecked = isSelected.value)
            }
        }
    }
}