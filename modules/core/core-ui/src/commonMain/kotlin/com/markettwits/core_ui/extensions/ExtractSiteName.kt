package com.markettwits.core_ui.extensions


import io.ktor.http.Url


fun String.extractSiteName(): String {
    return try {
        val uri =
            if (this.startsWith("http://") || this.startsWith("https://")) this else "http://$this"
        val url = Url(uri)
        val host = url.host
        run {
            val formattedHost = if (host.startsWith("www.")) host.substring(4) else host
            formattedHost.substringBefore('.')
        }
    } catch (e: Exception) {
        ""
    }
}