package com.markettwits.waifupics.view.extensions

import androidx.compose.ui.graphics.Color

fun hexToComposeColor(hex: String) = Color(android.graphics.Color.parseColor(hex))