package com.j4m1nion.j4player.player


import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.Keep
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.j4m1nion.j4player.player.composables.AppEventListener
import com.j4m1nion.j4player.player.composables.utils.getResizeParameter
import com.j4m1nion.j4player.player.composables.views.player.DrawControllerPlayPauseSkipsButtons
import com.j4m1nion.j4player.player.composables.views.player.DrawControllerSeekBars
import com.j4m1nion.j4player.player.composables.views.player.DrawControllerTitleAndTopControls
import com.j4m1nion.j4player.player.composables.views.player.DrawMediaFileList
import com.j4m1nion.j4player.player.composables.views.player.DrawMediaInfos
import com.j4m1nion.j4player.player.configuration.PlayerViewConfiguration
import com.j4m1nion.j4player.player.model.MediaFile
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    mediaFiles : List<MediaFile>,
    viewModel: PlayerViewModel,
    playerViewConfiguration: PlayerViewConfiguration = PlayerViewConfiguration(),
    loadingView: @Composable (BoxScope.() -> Unit) ? = null,
    errorView: @Composable (BoxScope.() -> Unit) ? = null){

    val configuration = LocalConfiguration.current
    val loading by viewModel.loading
    val playerError by viewModel.error
    val showController = remember { MutableTransitionState(false) }
    val play by viewModel.play
    val seekMode by viewModel.seekMode
    val currentMediaFile by remember { derivedStateOf { viewModel.playerMediaState.value.currentMediaFile } }
    val playerState by viewModel.playerState
    val volumeIsOn by remember { derivedStateOf { playerState.playerVolume != 0f } }
    val changeShuffle by viewModel.requestShuffle
    val changeAutoplay by viewModel.requestAutoplay
    var titleIsExpanded by rememberSaveable { mutableStateOf(false) }
    var playerExpanded by remember{ mutableStateOf(false) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var startOffsetY by remember { mutableFloatStateOf(0f) }
    val animateAlpha : Float by animateFloatAsState(
        targetValue = if(offsetY <= 50f && !playerExpanded) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing,
        )
    )


    LaunchedEffect(Unit) {
        viewModel.loadMedias(mediaFiles)
    }


    AppEventListener {
        when(it){
            Lifecycle.Event.ON_RESUME -> {
                viewModel.play()
            }
            Lifecycle.Event.ON_STOP -> {
                viewModel.pause()
            }
            else -> {}
        }
    }


    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

                        Box(modifier = Modifier.offset { IntOffset(0, if(configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) offsetY.roundToInt() else 0) }){
                            AndroidView(
                                factory = { ctx ->
                                    PlayerView(ctx).apply {
                                        resizeMode = configuration.getResizeParameter(playerViewConfiguration.controllerConfiguration)
                                        player = viewModel.exoplayer
                                        useController = false
                                        layoutParams = FrameLayout.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                        )
                                    }
                                },
                                update = {
                                    it.resizeMode = configuration.getResizeParameter(playerViewConfiguration.controllerConfiguration)
                                },
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .fillMaxWidth()
                                    .clickable {
                                        showController.targetState = !showController.currentState
                                    }
                                    .pointerInput(Unit){
                                        detectDragGestures(
                                            onDragStart = {
                                                startOffsetY = offsetY
                                                },
                                            onDrag = { change, dragAmount ->
                                                change.consume()
                                                offsetY += dragAmount.y
                                            },
                                            onDragEnd = {
                                                if(startOffsetY - offsetY > 200 || offsetY - startOffsetY > 200){
                                                   playerExpanded = !playerExpanded
                                                }
                                                offsetY = if(playerExpanded) (configuration.screenHeightDp).toFloat() else 0f
                                            },
                                            onDragCancel = {
                                                if(startOffsetY - offsetY > 200 || offsetY - startOffsetY > 200){
                                                   playerExpanded = !playerExpanded
                                                }
                                                offsetY = if(playerExpanded) (configuration.screenHeightDp).toFloat() else 0f
                                            }
                                        )
                                    }

                            )



                            if(playerViewConfiguration.controllerConfiguration.enabled){

                                DrawPlayerController(
                                    Modifier.matchParentSize(),
                                    showController,
                                    playerViewConfiguration,
                                    configuration.orientation,
                                    viewModel,
                                    play,
                                    seekMode,
                                    currentMediaFile,
                                    playerState.currentPositionPercent,
                                    playerState.currentTimeFormatted,
                                    playerState.durationFormatted,
                                    playerState.bufferedTimePercent,
                                    volumeIsOn)
                            }

                            when{
                                loading && loadingView != null -> loadingView.invoke(this)
                                loading && playerViewConfiguration.features.loading.enabled-> Box(modifier = Modifier
                                    .matchParentSize()
                                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                                playerError && errorView != null -> errorView.invoke(this)
                                playerError && playerViewConfiguration.features.error.enabled -> Box(modifier = Modifier
                                    .matchParentSize()
                                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.Center)
                                            .padding(horizontal = 20.dp),
                                        text = stringResource(R.string.player_error_text),
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.secondary,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                }
                            }
                        }
        Column(modifier = Modifier
            .alpha(animateAlpha)
            .offset { IntOffset(0, offsetY.roundToInt()) }

        ){
            if(playerViewConfiguration.features.mediaInfos.enabled){
                DrawMediaInfos(
                    modifier = Modifier
                        .clickable { titleIsExpanded = !titleIsExpanded },
                    playerViewModel = viewModel,
                    configuration = playerViewConfiguration.features.mediaInfos,
                    playlist = mediaFiles.size > 1,
                    shuffle = changeShuffle,
                    autoplay = changeAutoplay,
                    mediaFile = currentMediaFile,
                    expanded = titleIsExpanded,
                    onChapterSelected = { chapter ->
                        viewModel.selectChapter(chapter)

                    }
                )
            }
            DrawMediaFileList(mediaFiles, currentMediaFile, viewModel, playerError)
        }

    }




    DisposableEffect(Unit) {
        onDispose {
            viewModel.dispose()
        }
    }

}


@OptIn(UnstableApi::class)
@Composable
private fun DrawPlayerController(
    modifier: Modifier,
    showController: MutableTransitionState<Boolean>,
    playerViewConfiguration: PlayerViewConfiguration,
    orientation: Int,
    viewModel: PlayerViewModel,
    play: Boolean,
    seekMode: Boolean,
    mediaFile: MediaFile?,
    currentPosition: Float,
    currentTimeFormatted: String,
    durationFormatted : String,
    bufferedPosition: Float,
    playerVolumeOn: Boolean
) {

    LaunchedEffect(key1 = showController.currentState, key2 =  play, key3= seekMode) {
        if(showController.currentState){
            delay(1500)
            showController.targetState = !play || seekMode
        }
    }
    AnimatedVisibility(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.4f)),
        visibleState = showController,
        enter = playerViewConfiguration.animation.enterControllerAnimation,
        exit = playerViewConfiguration.animation.exitControllerAnimation
    ) {
        Box {
            DrawControllerPlayPauseSkipsButtons(
                modifier = Modifier.align(Alignment.Center),
                playerViewConfiguration = playerViewConfiguration,
                orientation = orientation,
                viewModel = viewModel,
                play = play)

            //TOP
            DrawControllerTitleAndTopControls(
                modifier = Modifier.align(Alignment.TopCenter),
                configuration = playerViewConfiguration.controllerConfiguration.topConfiguration,
                mediaFile = mediaFile,
                orientation = orientation)

            //BOTTOM
            DrawControllerSeekBars(
                modifier = Modifier.align(Alignment.BottomCenter),
                bufferedPosition = bufferedPosition,
                currentPosition = currentPosition,
                viewModel = viewModel,
                currentTimeFormatted = currentTimeFormatted,
                durationFormatted = durationFormatted,
                configuration = playerViewConfiguration.controllerConfiguration,
                volume = playerVolumeOn
            )
        }
    }
}





