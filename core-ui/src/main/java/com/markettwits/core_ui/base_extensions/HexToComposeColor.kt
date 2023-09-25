package com.markettwits.core_ui.base_extensions

import androidx.compose.ui.graphics.Color

fun hexToComposeColor(hex: String) = Color(android.graphics.Color.parseColor(hex))