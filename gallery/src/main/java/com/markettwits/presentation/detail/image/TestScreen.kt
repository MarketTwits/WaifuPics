package com.markettwits.presentation.detail.image

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.MediaViewBottomBar(
    showDeleteButton: Boolean = true,
    showUI: Boolean,
    paddingValues: PaddingValues,
    currentIndex: Int = 0,
    onDeleteMedia: ((Int) -> Unit)? = null,
) {
    AnimatedVisibility(
        visible = showUI,
//        enter = enterAnimation(DEFAULT_TOP_BAR_ANIMATION_DURATION),
//        exit = exitAnimation(DEFAULT_TOP_BAR_ANIMATION_DURATION),
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
        Row(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black)
                    )
                )
                .padding(
                    top = 24.dp,
                    bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Log.d("mt05", "show")
//            currentMedia?.let {
//                MediaViewActions(
//                    currentIndex = currentIndex,
//                    currentMedia = it,
//                    handler = handler,
//                    onDeleteMedia = onDeleteMedia,
//                    showDeleteButton = showDeleteButton
//                )
//            }
        }
    }
//    currentMedia?.let {
//        MediaInfoBottomSheet(
//            media = it,
//            state = bottomSheetState,
//            handler = handler
//        )
//    }
}