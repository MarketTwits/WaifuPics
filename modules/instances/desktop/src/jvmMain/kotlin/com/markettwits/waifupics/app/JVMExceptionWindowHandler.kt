package com.markettwits.waifupics.app

import java.awt.FlowLayout
import java.awt.Frame
import java.awt.Label

fun JVMExceptionWindowHandler(){
    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        java.awt.Dialog(Frame(), e.message ?: "Error").apply {
            layout = FlowLayout()
            val label = Label(e.message)
            add(label)
            val button = java.awt.Button("OK").apply {
                addActionListener { dispose() }
            }
            add(button)
            setSize(800, 500)
            isVisible = true
        }
    }
}