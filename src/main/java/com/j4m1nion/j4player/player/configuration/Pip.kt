package com.j4m1nion.j4player.player.configuration

import android.app.PictureInPictureParams
import android.util.Rational
import androidx.annotation.Keep

@Keep
fun getPipParams(): PictureInPictureParams {
    val aspectRatio = Rational(16,9)
    val params = PictureInPictureParams
        .Builder()
        .setAspectRatio(aspectRatio)
        .build()

    return params
}