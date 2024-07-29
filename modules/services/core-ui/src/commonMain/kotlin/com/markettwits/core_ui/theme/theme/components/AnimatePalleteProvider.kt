package com.markettwits.core_ui.theme.theme.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private const val ANIMATION_DURATION_MS = 750
private val animationSpec: AnimationSpec<Color> = tween(ANIMATION_DURATION_MS)

@Composable
private fun animateColor(
    targetValue: Color
) = animateColorAsState(
    targetValue = targetValue,
    animationSpec = animationSpec,
    label = "$targetValue"
).value

@Composable
internal fun ColorScheme.toAnimatePallet() = lightColorScheme(
    primary = animateColor(this.primary),
    onPrimary = animateColor(this.onPrimary),
    secondary = animateColor(this.secondary),
    onSecondary = animateColor(this.onSecondary),
    surfaceTint = animateColor(this.surfaceTint),
    primaryContainer = animateColor(this.primaryContainer),
    tertiary = animateColor(this.tertiary),
    onTertiary = animateColor(this.onTertiary),
    surface = animateColor(this.surface),
    onSurface = animateColor(this.onSurface),
    inversePrimary = animateColor(this.inversePrimary),
    onBackground = animateColor(this.onBackground),
    background = animateColor(this.background)
)