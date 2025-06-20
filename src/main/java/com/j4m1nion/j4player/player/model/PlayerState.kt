package com.j4m1nion.j4player.player.model

import androidx.compose.runtime.MutableState

data class PlayerState(
    val currentTime: Long = 0L,
    val currentTimeFormatted: String = "",
    val duration: Long = 0L,
    val durationFormatted: String = "",
    val bufferedTime: Long = 0L,
    val bufferedTimePercent: Float = 0F,
    val currentPositionPercent: Float = 0F,
    val playerVolume: Float = 1F
)

fun MutableState<PlayerState>.updateWith(
    currentTime: Long ? = null,
    currentTimeFormatted: String? = null,
    duration: Long? = null,
    durationFormatted: String? = null,
    bufferedTime: Long? = null,
    bufferedTimePercent: Float? = null,
    currentPositionPercent: Float? = null,
    playerVolume: Float? = null
){
    value = value.updateWith(
        currentTime,
        currentTimeFormatted,
        duration,
        durationFormatted,
        bufferedTime,
        bufferedTimePercent,
        currentPositionPercent,
        playerVolume)
}

fun PlayerState.updateWith(
    currentTime: Long ? = null,
    currentTimeFormatted: String? = null,
    duration: Long? = null,
    durationFormatted: String? = null,
    bufferedTime: Long? = null,
    bufferedTimePercent: Float? = null,
    currentPositionPercent: Float? = null,
    playerVolume: Float? = null
) : PlayerState {

    return this.copy(
        currentTime = currentTime ?: this.currentTime,
        currentTimeFormatted = currentTimeFormatted ?: this.currentTimeFormatted,
        duration = duration ?: this.duration,
        durationFormatted = durationFormatted ?: this.durationFormatted,
        bufferedTime = bufferedTime ?: this.bufferedTime,
        bufferedTimePercent = bufferedTimePercent ?: this.bufferedTimePercent,
        currentPositionPercent = currentPositionPercent ?: this.currentPositionPercent,
        playerVolume = playerVolume ?: this.playerVolume
    )

}