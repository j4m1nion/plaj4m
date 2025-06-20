package com.j4m1nion.j4player.player.composables.utils

import android.content.res.Configuration
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
import com.j4m1nion.j4player.player.configuration.PlayerControllerConfiguration

@OptIn(UnstableApi::class)
fun Configuration.getResizeParameter(configuration: PlayerControllerConfiguration): Int {
    return when{
        orientation == Configuration.ORIENTATION_PORTRAIT && configuration.zoomInPortrait -> RESIZE_MODE_FILL
        orientation == Configuration.ORIENTATION_LANDSCAPE && configuration.zoomInLandscape -> RESIZE_MODE_FILL
        else -> RESIZE_MODE_FIT
    }
}