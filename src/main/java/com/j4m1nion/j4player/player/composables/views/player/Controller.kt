package com.j4m1nion.j4player.player.composables.views.player

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.j4m1nion.j4player.player.PlayerViewModel
import com.j4m1nion.j4player.player.R
import com.j4m1nion.j4player.player.configuration.PlayerControllerConfiguration
import com.j4m1nion.j4player.player.configuration.PlayerViewConfiguration
import com.j4m1nion.j4player.player.configuration.PlayerViewTopConfiguration
import com.j4m1nion.j4player.player.model.MediaFile

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun DrawControllerSeekBars(
    modifier: Modifier,
    bufferedPosition: Float,
    currentPosition: Float,
    viewModel: PlayerViewModel?,
    currentTimeFormatted: String,
    durationFormatted: String,
    configuration: PlayerControllerConfiguration,
    volume: Boolean
) {
    Column(modifier) {

            Box(Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp).padding(bottom = 16.dp)){
                    if(configuration.bottomConfiguration.showVolume){
                        IconButton(modifier = Modifier.width(32.dp).height(32.dp).padding(horizontal = 4.dp), onClick = {viewModel?.changeVolume()}) {
                            Image(
                                painter = painterResource(
                                    if(volume) R.drawable.ic_volume_on
                                    else R.drawable.ic_volume_off
                                ),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                }

                Slider(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    value = bufferedPosition,
                    onValueChange = { },
                    valueRange = 0f..1f,
                    thumb = {},
                    track = { sliderState ->
                        val fraction by remember {
                            derivedStateOf {
                                (sliderState.value - sliderState.valueRange.start) /
                                        (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                            }
                        }

                        Box(Modifier.fillMaxWidth()) {
                            Box(
                                Modifier
                                    .fillMaxWidth(1f)
                                    .height(6.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer.copy(
                                            alpha = 0.4f
                                        ), CircleShape
                                    )
                            )
                            Box(
                                Modifier
                                    .fillMaxWidth(fraction)
                                    .height(6.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer.copy(
                                            alpha = 0.6f
                                        ), CircleShape
                                    )
                            )
                        }
                    }
                )
                Slider(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(vertical = 8.dp),
                    value = currentPosition,
                    onValueChange = { value: Float ->
                        viewModel?.userSeekIntent(value)
                    },
                    onValueChangeFinished = {
                        viewModel?.seekToPosition()
                    },
                    valueRange = 0f..1f,
                    thumb = {
                        Box(
                            Modifier
                                .size(24.dp)
                                .padding(4.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                        )
                    },
                    track = { sliderState ->
                        val fraction by remember {
                            derivedStateOf {
                                (sliderState.value - sliderState.valueRange.start) /
                                        (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                            }
                        }

                        Box(Modifier.fillMaxWidth()) {
                            Box(
                                Modifier
                                    .fillMaxWidth(fraction)
                                    .height(6.dp)
                                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                            )
                        }
                    }
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.0f),
                                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 1.0f)
                                )
                            )
                        )
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        currentTimeFormatted,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        durationFormatted,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }


        }

    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
 fun DrawControllerPlayPauseSkipsButtons(
    modifier: Modifier,
    playerViewConfiguration: PlayerViewConfiguration,
    orientation: Int,
    viewModel: PlayerViewModel?,
    play: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (playerViewConfiguration.controllerConfiguration.centerConfiguration.tapFastReplay) {
            Box(modifier = Modifier
                .width((100 * orientation).dp)
                .height((228 * orientation).dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            viewModel?.seekFastReplay10()
                        }
                    )
                }
            )
        }
        if (playerViewConfiguration.controllerConfiguration.centerConfiguration.showPreviousButton) {
           val visibility = viewModel?.isPlayingTheFirstVideo() == false
            PlayerOptionButton(
                modifier = Modifier.size(40.dp).alpha(
                    if(visibility) 1f else 0f
                ),
                visibility = visibility,
                onClickAction = {
                    viewModel?.skipPrevious()
                },
                icon = R.drawable.ic_skip_previous,
                contentDescription = R.string.button_skip_previous_cd
            )
        }
        if (playerViewConfiguration.controllerConfiguration.centerConfiguration.showFastReplay) {
            PlayerOptionButton(
                modifier = Modifier.size(40.dp),
                visibility = true,
                onClickAction = { viewModel?.seekFastReplay10() },
                icon = R.drawable.ic_replay10,
                contentDescription = R.string.button_replay10_cd
            )
        }
        if (playerViewConfiguration.controllerConfiguration.centerConfiguration.showPlayPauseButton) {
            PlayPauseButton(
                modifier = Modifier.size(80.dp),
                onClickAction = { viewModel?.playPause() },
                play = play,
                playIcon = R.drawable.ic_play,
                pauseIcon = R.drawable.ic_pause,
                playDesc = R.string.button_play_cd,
                pauseDesc = R.string.button_pause_cd
            )
        }
        if (playerViewConfiguration.controllerConfiguration.centerConfiguration.showFastForward) {
            PlayerOptionButton(
                modifier = Modifier.size(40.dp),
                visibility = true,
                onClickAction = { viewModel?.seekFastForward10() },
                icon = R.drawable.ic_forward10,
                contentDescription = R.string.button_forward10_cd
            )
        }
        if (playerViewConfiguration.controllerConfiguration.centerConfiguration.showNextButton) {
            val visibility = viewModel?.isPlayingTheLastVideo() == false
            PlayerOptionButton(
                modifier = Modifier.size(40.dp).alpha(if(visibility) 1f else 0f),
                visibility = visibility,
                onClickAction = {  viewModel?.skipNext() },
                icon = R.drawable.ic_skip_next,
                contentDescription = R.string.button_skip_next_cd
            )
        }
        if (playerViewConfiguration.controllerConfiguration.centerConfiguration.tapFastForward) {
            Box(modifier = Modifier
                .width((100 * orientation).dp)
                .height((228 * orientation).dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            viewModel?.seekFastForward10()
                        }
                    )
                }
            )
        }
    }
}


@androidx.annotation.OptIn(UnstableApi::class)
@Composable
 fun DrawControllerTitleAndTopControls(
    modifier: Modifier,
    configuration: PlayerViewTopConfiguration,
    mediaFile: MediaFile?,
    orientation: Int) {
    Row(
        horizontalArrangement = when{
            orientation == ORIENTATION_PORTRAIT && configuration.showTitleInPortrait -> Arrangement.SpaceBetween
            orientation == ORIENTATION_LANDSCAPE && configuration.showTitleInLandscape -> Arrangement.SpaceBetween
            else -> Arrangement.End
        },
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .fillMaxWidth()
    ) {
        if (
            ((orientation == ORIENTATION_PORTRAIT && configuration.showTitleInPortrait) ||
                    (orientation == ORIENTATION_LANDSCAPE && configuration.showTitleInLandscape))
                        && mediaFile?.title?.isNotBlank() == true) {
            Text(
                text = mediaFile.title,
                modifier = Modifier.weight(1F, false),
                style = when (orientation) {
                    ORIENTATION_PORTRAIT -> MaterialTheme.typography.labelLarge
                    ORIENTATION_LANDSCAPE -> MaterialTheme.typography.headlineMedium
                    else -> MaterialTheme.typography.headlineMedium
                }
                 ,
                color = MaterialTheme.colorScheme.secondary
            )
        }

    }
}


