package com.markettwits.waifupics.random.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.markettwits.core_ui.provider.ApplicationViewModel
import com.markettwits.waifupics.filter.components.AgeRatingFilter
import com.markettwits.waifupics.random.components.bottom_pannel.ConfigureBottomPanel
import com.markettwits.waifupics.random.components.image_info.image_card_info.loading.ImageCardInfoLoading
import com.markettwits.waifupics.random.components.image_info.image_card_info.success.ImageInfoCardEmptyAuthor
import com.markettwits.waifupics.random.components.image_state.fuckup.ImageFuckup
import com.markettwits.waifupics.random.components.image_state.loading.ImageLoading
import com.markettwits.waifupics.random.components.image_state.suceess.ImageCardContent
import com.markettwits.waifupics.random.model.RandomImageState
import com.markettwits.waifupics.random.viewmodel.ImageViewModel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun RandomImageScreen(
    viewModel: ImageViewModel = ApplicationViewModel(),
    paddingValues: PaddingValues
) {
    val state = viewModel.state().collectAsState()
    val imageState = viewModel.loadedImageState().collectAsState()
    val scope = rememberCoroutineScope()

    val offsetY = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }

    val swipeThreshold = 300f // Порог для переключения

    // Следим за изменениями состояния для анимации появления нового изображения
    LaunchedEffect(state.value) {
        if (state.value is RandomImageState.Success) {
            // Анимация появления снизу при загрузке нового изображения
            offsetY.snapTo(1000f)
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        contentAlignment = Alignment.Center
    ) {
        // Контент с изображением - обрабатывает свайпы
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset { IntOffset(0, offsetY.value.roundToInt()) }
                .scale(
                    // Немного уменьшаем при свайпе для визуального эффекта
                    scaleX = 1f - (offsetY.value.absoluteValue / 2000f).coerceIn(0f, 0.1f),
                    scaleY = 1f - (offsetY.value.absoluteValue / 2000f).coerceIn(0f, 0.1f)
                )
                .alpha(
                    // Затемняем при сильном свайпе
                    1f - (offsetY.value.absoluteValue / 1000f).coerceIn(0f, 0.3f)
                )
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onDragStart = {
                            scope.launch {
                                offsetY.stop()
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                // Если свайп вверх больше порога - переключаем на следующее
                                if (offsetY.value < -swipeThreshold) {
                                    // Анимируем уход изображения
                                    offsetY.animateTo(
                                        targetValue = -2000f,
                                        animationSpec = tween(300)
                                    )
                                    viewModel.fetchRandomImage()
                                } else {
                                    // Возвращаем в исходное положение с пружинкой
                                    offsetY.animateTo(
                                        targetValue = 0f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        )
                                    )
                                }
                            }
                        },
                        onDragCancel = {
                            scope.launch {
                                offsetY.animateTo(0f)
                            }
                        },
                        onVerticalDrag = { _, dragAmount ->
                            scope.launch {
                                // Ограничиваем драг только вверх для переключения
                                // Вниз можно тянуть немного для визуального эффекта
                                val newOffset = offsetY.value + dragAmount
                                val finalOffset = when {
                                    newOffset < 0 -> newOffset // Свайп вверх - без ограничений
                                    newOffset > 200f -> 200f // Свайп вниз - ограничен
                                    else -> newOffset
                                }
                                offsetY.snapTo(finalOffset)
                            }
                        }
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 800.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Основной контент с изображением
                when (val value = state.value) {
                    is RandomImageState.Error -> {
                        ImageFuckup(message = value.message)
                    }

                    is RandomImageState.Initial -> {}

                    is RandomImageState.Progress -> {
                        ImageLoading()
                        ImageCardInfoLoading()
                    }

                    is RandomImageState.Success -> {
                        ImageCardContent(
                            imageUrl = value.imageUrl,
                            id = value.id,
                            onChangeImageState = viewModel::obtainImageState
                        )
                        ImageInfoCardEmptyAuthor(
                            imageData = value.imageData,
                            colorPalette = value.colorPalette
                        )
                    }
                }
            }
        }

        // Панель управления и фильтры - всегда внизу, не двигаются
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .widthIn(max = 800.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConfigureBottomPanel(
                imageState = imageState.value,
                imageId = if (state.value is RandomImageState.Success)
                    (state.value as RandomImageState.Success).id else 0,
                onClickFetchRandomImage = {
                    scope.launch {
                        viewModel.fetchRandomImage()
                    }
                },
                onClickShareImage = viewModel::onClickShareImage,
                onClickAddToFavorite = viewModel::onClickAddToFavorite
            )
            AgeRatingFilter()
        }
    }
}