package com.markettwits.presentation.screens.detail.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
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
import com.markettwits.presentation.animations.Animation
import com.markettwits.presentation.screens.detail.GalleryScreenViewModel
import com.markettwits.presentation.screens.detail.button.DeleteButton
import com.markettwits.presentation.screens.detail.button.ShareButton
import com.markettwits.core_ui.theme.BlackScrim

@Composable
fun BoxScope.DownBarScreenImage(
    viewModel: GalleryScreenViewModel,
    showUI: Boolean,
    //paddingValues: PaddingValues
) {
    AnimatedVisibility(
        visible = showUI,
        enter = Animation.enterAnimation(Animation.DEFAULT_TOP_BAR_ANIMATION_DURATION),
        exit = Animation.exitAnimation(Animation.DEFAULT_TOP_BAR_ANIMATION_DURATION),
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
        Row(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, BlackScrim)
                    )
                )
                .padding(
                    top = 24.dp,
                   // bottom = paddingValues.calculateBottomPadding()
                )
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            )
        {
            ShareButton(followTheme = true) {
                viewModel.shareImage()
            }
            DeleteButton(followTheme = true) {
                viewModel.delete()
            }
        }
    }
}