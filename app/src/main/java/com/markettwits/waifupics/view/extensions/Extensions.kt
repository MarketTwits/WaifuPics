package com.markettwits.waifupics.view.extensions


import android.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.graphics.toColor

import java.net.URI


inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

fun String.extractSiteName(): String {
    return try {
        val uri =
            URI(if (this.startsWith("http://") || this.startsWith("https://")) this else "http://$this")
        var host = uri.host
        if (host != null) {
            host = if (host.startsWith("www.")) host.substring(4) else host
            host = host.substringBefore('.')
        }
        host
    } catch (e: Exception) {
        ""
    }
}

fun String.color(hex: String): Color {
    return Color.parseColor(hex).toColor()
}
