package com.markettwits.theme.components

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlin.test.Test
import kotlin.test.assertTrue

class ColorContrastTest {
    @Test fun lightThemeKeepsReadableMaterialContent() = verify(LightColorScheme)
    @Test fun darkThemeKeepsReadableMaterialContent() = verify(DarkColorScheme)

    private fun verify(scheme: ColorScheme) {
        val roles = mapOf(
            "background" to (scheme.background to scheme.onBackground),
            "surface" to (scheme.surface to scheme.onSurface),
            "sheet" to (scheme.surfaceContainerLow to scheme.onSurface),
            "sheet secondary text" to (scheme.surfaceContainerLow to scheme.onSurfaceVariant),
            "action surface" to (scheme.surfaceContainerHigh to scheme.onSurface),
            "primary button" to (scheme.primary to scheme.onPrimary),
            "tonal button" to (scheme.secondaryContainer to scheme.onSecondaryContainer),
            "selected item" to (scheme.primaryContainer to scheme.onPrimaryContainer),
            "error" to (scheme.errorContainer to scheme.onErrorContainer),
            "links" to (scheme.background to scheme.secondary),
            "outlined actions" to (scheme.surfaceContainerLow to scheme.secondary)
        )
        roles.forEach { (name, colors) ->
            val (background, foreground) = colors
            assertTrue(contrast(background, foreground) >= 4.5f, "$name must have at least 4.5:1 contrast")
            assertTrue(background.alpha == 1f && foreground.alpha == 1f, "$name must be opaque")
        }
    }

    private fun contrast(a: Color, b: Color): Float {
        val light = maxOf(a.luminance(), b.luminance())
        val dark = minOf(a.luminance(), b.luminance())
        return (light + 0.05f) / (dark + 0.05f)
    }
}
