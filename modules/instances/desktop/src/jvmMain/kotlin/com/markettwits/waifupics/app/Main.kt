package com.markettwits.waifupics.app

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.markettwits.core_ui.provider.SetContentLocal
import com.markettwits.waifupics.root.core.DefaultWaifuPicsApp
import com.markettwits.waifupics.root.core.MainScreen
import javax.imageio.ImageIO

private val appIcon by lazy {
    val image = object {}.javaClass.getResourceAsStream("/WaifuPics.png")
        ?.use(ImageIO::read)
        ?: error("Missing desktop app icon resource: WaifuPics.png")
    BitmapPainter(image.toComposeImageBitmap())
}

fun main() {

    JVMExceptionWindowHandler()
    DefaultWaifuPicsApp()
    application {
        Window(
            title = "WaifuPics",
            icon = appIcon,
            onCloseRequest = { exitApplication() }
        ) {
            SetContentLocal({}) {
                 MainScreen()
            }
        }
    }
}
