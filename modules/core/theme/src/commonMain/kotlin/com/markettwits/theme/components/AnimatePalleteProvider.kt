package com.markettwits.theme.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
private fun animateColor(targetValue: Color) = animateColorAsState(
    targetValue = targetValue,
    animationSpec = tween(350),
    label = "Theme color"
).value

@Composable
internal fun ColorScheme.toAnimatePallet() = copy(
    primary = animateColor(primary),
    onPrimary = animateColor(onPrimary),
    primaryContainer = animateColor(primaryContainer),
    onPrimaryContainer = animateColor(onPrimaryContainer),
    inversePrimary = animateColor(inversePrimary),
    secondary = animateColor(secondary),
    onSecondary = animateColor(onSecondary),
    secondaryContainer = animateColor(secondaryContainer),
    onSecondaryContainer = animateColor(onSecondaryContainer),
    tertiary = animateColor(tertiary),
    onTertiary = animateColor(onTertiary),
    tertiaryContainer = animateColor(tertiaryContainer),
    onTertiaryContainer = animateColor(onTertiaryContainer),
    background = animateColor(background),
    onBackground = animateColor(onBackground),
    surface = animateColor(surface),
    onSurface = animateColor(onSurface),
    surfaceVariant = animateColor(surfaceVariant),
    onSurfaceVariant = animateColor(onSurfaceVariant),
    surfaceTint = animateColor(surfaceTint),
    inverseSurface = animateColor(inverseSurface),
    inverseOnSurface = animateColor(inverseOnSurface),
    error = animateColor(error),
    onError = animateColor(onError),
    errorContainer = animateColor(errorContainer),
    onErrorContainer = animateColor(onErrorContainer),
    outline = animateColor(outline),
    outlineVariant = animateColor(outlineVariant),
    surfaceBright = animateColor(surfaceBright),
    surfaceDim = animateColor(surfaceDim),
    surfaceContainerLowest = animateColor(surfaceContainerLowest),
    surfaceContainerLow = animateColor(surfaceContainerLow),
    surfaceContainer = animateColor(surfaceContainer),
    surfaceContainerHigh = animateColor(surfaceContainerHigh),
    surfaceContainerHighest = animateColor(surfaceContainerHighest),
    scrim = animateColor(scrim),
    primaryFixed = animateColor(primaryFixed),
    primaryFixedDim = animateColor(primaryFixedDim),
    onPrimaryFixed = animateColor(onPrimaryFixed),
    onPrimaryFixedVariant = animateColor(onPrimaryFixedVariant),
    secondaryFixed = animateColor(secondaryFixed),
    secondaryFixedDim = animateColor(secondaryFixedDim),
    onSecondaryFixed = animateColor(onSecondaryFixed),
    onSecondaryFixedVariant = animateColor(onSecondaryFixedVariant),
    tertiaryFixed = animateColor(tertiaryFixed),
    tertiaryFixedDim = animateColor(tertiaryFixedDim),
    onTertiaryFixed = animateColor(onTertiaryFixed),
    onTertiaryFixedVariant = animateColor(onTertiaryFixedVariant),
)
