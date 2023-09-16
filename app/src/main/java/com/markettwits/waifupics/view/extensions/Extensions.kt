package com.markettwits.waifupics.view.extensions


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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

fun List<String>.extractSiteNames(): List<String> {
    return this.mapNotNull { url ->
        try {
            val uri =
                URI(if (url.startsWith("http://") || url.startsWith("https://")) url else "http://$url")
            var host = uri.host
            if (host != null) {
                host = if (host.startsWith("www.")) host.substring(4) else host
                host = host.substringBefore('.')
            }
            host
        } catch (e: Exception) {
            null // Ignore invalid URLs or other exceptions
        }
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


//    return this { url ->
//        try {
//            val uri = URI(if (url.startsWith("http://") || url.startsWith("https://")) url else "http://$url")
//            var host = uri.host
//            if (host != null) {
//                host = if (host.startsWith("www.")) host.substring(4) else host
//                host = host.substringBefore('.')
//            }
//            host
//        } catch (e: Exception) {
//            null // Ignore invalid URLs or other exceptions
//        }
//    }


//fun String.color(hex: String): Color {
//    return Color.parseColor(hex).toColor()
//}
