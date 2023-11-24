package com.markettwits.core.wrappers

import android.util.Log

interface Logger {
    /**
     * Send a {@link #DEBUG} log message.
     * @param message The message you would like logged.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     *        Default value = "mt05"
     */
    fun log(message: String)
    class Debug(private val tag: String = "mt05") : Logger {
        override fun log(message: String) {
            Log.d(tag, message)
        }
    }

    class Error(private val tag: String = "mt05") : Logger {
        override fun log(message: String) {
            Log.e(tag, message)
        }
    }
}