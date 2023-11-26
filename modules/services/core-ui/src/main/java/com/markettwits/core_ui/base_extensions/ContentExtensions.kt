package com.markettwits.core_ui.base_extensions

import android.content.Context
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.accessibility.AccessibilityManager

fun View.vibrate() = reallyPerformHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
@Suppress("DEPRECATION")
private fun View.reallyPerformHapticFeedback(feedbackConstant: Int) {
    if (context.isTouchExplorationEnabled()) {
        // Don't mess with a blind person's vibrations
        return
    }
    // Either this needs to be set to true, or android:hapticFeedbackEnabled="true" needs to be set in XML
    isHapticFeedbackEnabled = true

    /**
     * [HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING]
     * is deprecated in Android 13+
     */
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        performHapticFeedback(feedbackConstant)
    } else {
        // Most of the constants are off by default: for example, clicking on a button doesn't cause the phone to vibrate anymore
        // if we still want to access this vibration, we'll have to ignore the global settings on that.
        performHapticFeedback(feedbackConstant, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
    }
}
private fun Context.isTouchExplorationEnabled(): Boolean {
    // can be null during unit tests
    val accessibilityManager =
        getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager?
    return accessibilityManager?.isTouchExplorationEnabled ?: false
}