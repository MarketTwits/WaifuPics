package com.markettwits.core_ui.base_extensions


import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import io.ktor.http.Url


inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit,
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
        val uri = if (this.startsWith("http://") || this.startsWith("https://")) this else "http://$this"
        val url = Url(uri)
        val host = url.host
        run {
            val formattedHost = if (host.startsWith("www.")) host.substring(4) else host
            formattedHost.substringBefore('.')
        }
    } catch (e: Exception) {
        ""
    }
//    return try {
//            URI(if (this.startsWith("http://") || this.startsWith("https://")) this else "http://$this")
//        var host = uri.host
//        if (host != null) {
//            host = if (host.startsWith("www.")) host.substring(4) else host
//            host = host.substringBefore('.')
//        }
//        host
//    } catch (e: Exception) {
//        ""
//    }
}

