package com.markettwits.core_ui.base_extensions

import androidx.compose.ui.graphics.Color

fun rgbToComposeColor(color : List<Int>) = Color(android.graphics.Color.rgb(color[0], color[1], color[2]))