package com.markettwits.image_action.api

import com.sun.jna.Native
import com.sun.jna.win32.W32APIOptions
import java.awt.Frame
import java.awt.Image
import javax.swing.ImageIcon
import javax.swing.JOptionPane

object SystemWallpaperManager {

    fun acceptChangeSystemWallpaper(
        filePath: String
    ) {
        val options = arrayOf("Ok", "Cancel")
        val frame = Frame()
        var imageIcon = ImageIcon(filePath)
        val image: Image = imageIcon.image
        val newimg: Image =
            image.getScaledInstance(160, 160, Image.SCALE_SMOOTH)
        imageIcon = ImageIcon(newimg) // transform it back
        val dialogResult = JOptionPane.showOptionDialog(
            frame,
            "Do you want to change system wallpaper on this image ?", "Change wallpaper",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            imageIcon,
            options,
            options[1]
        )
        if (dialogResult == 0) {
            setWallpaper(filePath)
        }
    }

    @JvmStatic
    private fun setWallpaper(filePath: String) {
        User32.INSTANCE.SystemParametersInfo(
            0x0014,
            0,
            filePath,
            1
        )
    }

    internal interface User32 : com.sun.jna.Library {

        fun SystemParametersInfo(one: Int, two: Int, s: String?, three: Int): Boolean

        companion object {

            val INSTANCE: User32 = Native.loadLibrary(
                "user32",
                User32::class.java, W32APIOptions.DEFAULT_OPTIONS
            ) as User32
        }
    }

}