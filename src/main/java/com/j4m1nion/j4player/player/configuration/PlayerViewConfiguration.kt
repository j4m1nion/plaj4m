package com.j4m1nion.j4player.player.configuration

import androidx.annotation.Keep
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@Keep
data class PlayerViewConfiguration(
    val controllerConfiguration: PlayerControllerConfiguration = PlayerControllerConfiguration(),
    val animation : PlayerViewAnimation = PlayerViewAnimation(),
    val features : PlayerFeatureConfiguration = PlayerFeatureConfiguration()
)

@Keep
data class PlayerFeatureConfiguration(
    val mediaInfos : PlayerFeatureMediaInfos = PlayerFeatureMediaInfos(),
    val error : PlayerErrorConfiguration = PlayerErrorConfiguration(),
    val loading : PlayerLoadingConfiguration = PlayerLoadingConfiguration()
)

@Keep
data class PlayerLoadingConfiguration(
    val enabled: Boolean = true
)


@Keep
data class PlayerErrorConfiguration(
    val enabled: Boolean = true,
)

@Keep
data class PlayerFeatureMediaInfos(
    val enabled : Boolean = true,
    val shuffle: Boolean = true,
    val autoplay: Boolean = true,
    val title: Boolean = true,
    val description: Boolean = true,
    val chapters: Boolean = true
)

@Keep
data class PlayerControllerConfiguration(
    val enabled: Boolean = true,
    val zoomInLandscape: Boolean = true,
    val zoomInPortrait: Boolean = false,
    val topConfiguration : PlayerViewTopConfiguration = PlayerViewTopConfiguration(),
    val centerConfiguration : PlayerViewCenterConfiguration = PlayerViewCenterConfiguration(),
    val bottomConfiguration : PlayerViewBottomConfiguration = PlayerViewBottomConfiguration()
)

@Keep
data class PlayerViewTopConfiguration(
    val showTitleInPortrait: Boolean = false,
    val showTitleInLandscape: Boolean = false)

@Keep
data class PlayerViewCenterConfiguration(
    val showPlayPauseButton : Boolean = true,
    val showFastForward : Boolean = false,
    val showFastReplay : Boolean = false,
    val showNextButton : Boolean = true,
    val showPreviousButton : Boolean = true,
    val tapFastForward : Boolean = !showFastForward,
    val tapFastReplay : Boolean = !showFastReplay
)

@Keep
data class PlayerViewBottomConfiguration(val showVolume: Boolean = true)

@Keep
data class PlayerViewAnimation(
    val enterControllerAnimation : EnterTransition = fadeIn(
        animationSpec = tween(
            durationMillis = 750,
            easing = LinearOutSlowInEasing
        ),
    ),
    val exitControllerAnimation : ExitTransition = fadeOut(
        animationSpec = tween(
            durationMillis = 750,
            easing = LinearOutSlowInEasing
        )
    )
)