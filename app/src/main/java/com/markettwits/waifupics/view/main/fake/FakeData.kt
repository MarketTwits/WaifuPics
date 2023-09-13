package com.markettwits.waifupics.view.main.fake

import androidx.compose.ui.graphics.Color

fun hexToComposeColor(hex: String) = Color(android.graphics.Color.parseColor(hex))
fun hexToColor(hex: String): Color {
    // Remove the '#' symbol if it exists in the hex string
    val cleanHex = if (hex.startsWith("#")) hex.substring(1) else hex

    // Convert the cleaned hex string to RGB values
    val red = Integer.parseInt(cleanHex.substring(0, 2), 16) / 255f
    val green = Integer.parseInt(cleanHex.substring(2, 4), 16) / 255f
    val blue = Integer.parseInt(cleanHex.substring(4, 6), 16) / 255f
    return Color(red, green, blue, alpha = 1.0f)
}

fun fakeHex() = listOf(
    "#eedcb5",
    "#2d2224",
    "#60504d",
    "#d5ac87",
    "#a27d5c",
    "#b19d87",
    "#736d8c",
    "#bfb5b5",
    "#47210f"
)
