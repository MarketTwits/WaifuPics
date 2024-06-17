package com.markettwits.presentation.screens.detail.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.markettwits.core_ui.di.LocalRootPadding
import com.markettwits.core_ui.base_extensions.noRippleClickable
import com.markettwits.presentation.animations.Animation
import com.markettwits.presentation.screens.detail.GalleryScreenViewModel
import com.markettwits.presentation.screens.detail.info.InfoImageBottomSheet
import com.markettwits.core_ui.theme.BlackScrim


@Composable
fun BoxScope.TopBarScreenImage(viewModel: GalleryScreenViewModel, showUI: Boolean) {
    val item by viewModel.currentImage().collectAsState()
    var infoImage by remember {
        mutableStateOf(false)
    }
    AnimatedVisibility(
        visible = showUI,
        enter = Animation.enterAnimation(Animation.DEFAULT_TOP_BAR_ANIMATION_DURATION),
        exit = Animation.exitAnimation(Animation.DEFAULT_TOP_BAR_ANIMATION_DURATION),
    ) {
        Row(
            Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(BlackScrim, Color.Transparent)
                    )
                )
                .padding(top = LocalRootPadding.current.calculateTopPadding())
                .padding(start = 5.dp, end = 8.dp)
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .padding(10.dp)
                    .noRippleClickable {
                        viewModel.onClickPop()
                    },
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "back",
                tint = Color.White
            )
            Text(
                text = item.created,
                modifier = Modifier.padding(start = 30.dp),
                color = Color.White,
                fontSize = 16.sp
            )
            IconButton(onClick = { infoImage = !infoImage }) {
                Icon(Icons.Default.MoreVert, "", tint = Color.White)
            }
            if (infoImage) {
                InfoImageBottomSheet() {
                    infoImage = false
                }
            }
        }
    }
}

