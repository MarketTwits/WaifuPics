package com.markettwits.core_ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat


fun openWebPage(url: String, context: Context) {
    var webpage = Uri.parse(url)
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        webpage = Uri.parse("http://$url")
    }
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    ContextCompat.startActivity(context, intent, null)

}
