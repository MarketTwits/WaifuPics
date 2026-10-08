package com.markettwits.waifupics.random.components.image_state.suceess

import android.os.Build

internal actual fun supportsBackdropBlur(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
