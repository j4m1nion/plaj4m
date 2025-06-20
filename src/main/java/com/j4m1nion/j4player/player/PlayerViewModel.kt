package com.j4m1nion.j4player.player

import android.app.Application
import android.util.Log
import androidx.annotation.Keep
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.j4m1nion.j4player.player.arch.Timer
import com.j4m1nion.j4player.player.model.MediaChapter
import com.j4m1nion.j4player.player.model.MediaFile
import com.j4m1nion.j4player.player.model.PlayerMediaState
import com.j4m1nion.j4player.player.model.PlayerState
import com.j4m1nion.j4player.player.model.mapper.buildMediaItem
import com.j4m1nion.j4player.player.model.updateWith
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoField
import java.util.Locale
import java.util.concurrent.TimeUnit

@UnstableApi
@Keep
open class PlayerViewModel(
    application: Application,
    val exoplayer: ExoPlayer = ExoPlayer
        .Builder(application)
        .setTrackSelector(DefaultTrackSelector(application, AdaptiveTrackSelection.Factory()))
        .setLoadControl(DefaultLoadControl())
        .build(),
    private val listener : Player.Listener = PlayerListener()) : AndroidViewModel(application) {

        companion object{
            const val TAG = "PlayerViewModel"
        }

    private val mediaSession = MediaSession.Builder(application, exoplayer).build().apply {
        this.player = exoplayer
    }

    val loading = mutableStateOf(true)
    val error = mutableStateOf(false)
    val playerMediaState = mutableStateOf(PlayerMediaState())
    private val playerEvents = mutableStateOf<PlayerEvent>(PlayerEvent.Idle)
    val playerState = mutableStateOf(PlayerState())

    var seekMode = mutableStateOf(false)

    var requestZoom = mutableStateOf(value = false)
    var requestShuffle = mutableStateOf(false)
    var requestAutoplay = mutableStateOf(false)

    val play = mutableStateOf(exoplayer.playWhenReady)

    private val seekBarJob = {
                playerState.updateWith(
                    currentTime = exoplayer.currentPosition,
                    duration = exoplayer.duration,
                    durationFormatted = if(exoplayer.duration > 0){
                        formatTime(exoplayer.duration)
                    } else null,
                    bufferedTime = exoplayer.bufferedPosition,
                    playerVolume = exoplayer.volume,
                    bufferedTimePercent = exoplayer.bufferedPosition.toFloat() / exoplayer.duration.toFloat(),
                    currentPositionPercent = exoplayer.currentPosition.toFloat() / exoplayer.duration.toFloat(),
                    currentTimeFormatted = if(exoplayer.currentPosition > 0){
                        formatTime(exoplayer.currentPosition)
                    }
                    else null
                )
    }

    init {
        Timer.init(seekBarJob)
        exoplayer.also {
            it.addListener(listener)
            observePlayerEvents()
            Log.d(TAG, "Setup player $it")
        }
    }


    private fun observePlayerEvents(){
        if(listener is PlayerListener){
            listener.events.onEach {
                when(it){
                    PlayerEvent.Play -> Timer.start(viewModelScope, delay = 1000L)
                    PlayerEvent.Pause -> Timer.stop()
                    PlayerEvent.Buffering -> loading.value = true
                    PlayerEvent.Ended -> {
                        loadNextItemIfAutoplay()
                    }
                    PlayerEvent.Idle -> {}
                    PlayerEvent.Ready -> {
                        loading.value = false
                    }

                    is PlayerEvent.Error -> {
                        Log.e(TAG, "Player Error : ${it.error}")
                        loading.value = false
                        error.value = true
                    }

                    is PlayerEvent.AudioAttributesChanged -> Log.i(TAG, "AudioAttributesChanged: ${it.audioAttributes}")
                    is PlayerEvent.AvailableCommandsChanged -> Log.i(TAG, "AvailableCommandsChanged: ${it.availableCommands}")
                    is PlayerEvent.Cues -> Log.i(TAG, "Cues: ${it.cueGroup}")
                    is PlayerEvent.DeviceInfoChanged -> Log.i(TAG, "DeviceInfoChanged: ${it.deviceInfo}")
                    is PlayerEvent.DeviceVolumeChanged -> Log.i(TAG, "DeviceVolumeChanged: volume(${it.volume}), muted(${it.muted})")
                    is PlayerEvent.ErrorChanged -> Log.i(TAG, "ErrorChanged: ${it.error}")
                    is PlayerEvent.Events -> Log.i(TAG, "Events: ${it.events}")
                    is PlayerEvent.IsLoading -> Log.i(TAG, "IsLoading: ${it.loading}")
                    is PlayerEvent.MaxSeekToPreviousPositionChanged -> Log.i(TAG, "MaxSeekToPreviousPositionChanged: ${it.maxSeekToPreviousPositionMs}")
                    is PlayerEvent.MediaItemTransitioned -> Log.i(TAG, "MediaItemTransitioned: mediaItem(${it.mediaItem}), reason(${it.reason})")
                    is PlayerEvent.MediaMetadataChanged -> Log.i(TAG, "MediaMetadataChanged: ${it.mediaMetadata}")
                    is PlayerEvent.NotPlayWhenReady -> Log.i(TAG, "NotPlayWhenReady reason: ${it.reason}")
                    is PlayerEvent.PlayWhenReady -> Log.i(TAG, "PlayWhenReady reason: ${it.reason}")
                    is PlayerEvent.PlaybackParametersChanged -> Log.i(TAG, "PlaybackParametersChanged: ${it.playbackParameters}")
                    is PlayerEvent.PlaybackSuppressionReasonChanged -> Log.i(TAG, "PlaybackSuppressionReasonChanged: ${it.playbackSuppressionReason}")
                    is PlayerEvent.PlaylistMetadataChanged -> Log.i(TAG, "PlaylistMetadataChanged: ${it.mediaMetadata}")
                    is PlayerEvent.PositionDiscontinued -> Log.i(TAG, "PositionDiscontinued: old(${it.oldPosition}), new(${it.newPosition}), reason(${it.reason})")
                    PlayerEvent.RenderedFirstFrame -> Log.i(TAG, "RenderedFirstFrame")
                    is PlayerEvent.RepeatModeChanged -> Log.i(TAG, "RepeatModeChanged: ${it.repeat}")
                    is PlayerEvent.SeekBack -> Log.i(TAG, "SeekBack: ${it.seekBackMs}")
                    is PlayerEvent.SeekForward -> Log.i(TAG, "SeekForward: ${it.seekForwardMs}")
                    is PlayerEvent.ShuffleModeChanged -> Log.i(TAG, "ShuffleModeChanged: ${it.shuffle}")
                    is PlayerEvent.SkipSilenceEnabledChanged -> Log.i(TAG, "SkipSilenceEnabledChanged: ${it.skipSilence}")
                    is PlayerEvent.SurfaceSizeChanged -> Log.i(TAG, "SurfaceSizeChanged: width(${it.width}), height(${it.height})")
                    is PlayerEvent.TimeLineChanged -> Log.i(TAG, "TimeLineChanged: timeline(${it.timeline}), reason(${it.reason})")
                    is PlayerEvent.TrackSelectionParametersChanged -> Log.i(TAG, "TrackSelectionParametersChanged: ${it.parameters}")
                    is PlayerEvent.TracksChanged -> Log.i(TAG, "TracksChanged: ${it.tracks}")
                    is PlayerEvent.VideoSizeChanged -> Log.i(TAG, "VideoSizeChanged: ${it.videoSize}")
                    is PlayerEvent.VolumeChanged -> Log.i(TAG, "VolumeChanged: ${it.volumeChanged}")
                }
                playerEvents.value = it
            }.launchIn(viewModelScope)
        }
    }


   private fun loadMedia(mediaFile : MediaFile, playWhenReady : Boolean = true){
       loading.value = true
       error.value = false
       exoplayer.run {
           setMediaItem(mediaFile.buildMediaItem())
           prepare()
           this.playWhenReady = playWhenReady
           play.value = this.playWhenReady
           playerMediaState.updateWith(
               currentMediaFile = mediaFile,
               currentPlaylistIndex = playerMediaState.value.playlist.indexOf(mediaFile)
           )
       }

   }

    fun loadMediaFromList(mediaFile: MediaFile){
        loadMedia(mediaFile, exoplayer.playWhenReady)
    }

    private fun loadNextItemIfAutoplay(){
        if(requestAutoplay.value){
            skipNext()
        }
    }

    fun loadMedias(mediaFiles : List<MediaFile>, playWhenReady: Boolean = true){
        playerMediaState.updateWith(
            playlist = mediaFiles
        )
        playerMediaState.value.getCurrentMedia()?.apply {
            loadMedia(this, playWhenReady)
        }?: kotlin.run{ playerEvents.value = PlayerEvent.Error(java.lang.Exception("no media loaded"))}
    }

    fun playPause() {
        when(exoplayer.playWhenReady){
            true -> exoplayer.pause()
            false -> exoplayer.play()
        }
        play.value = exoplayer.playWhenReady
    }

    fun play(){
        exoplayer.play()
        play.value = exoplayer.playWhenReady
    }

    fun pause(){
        exoplayer.pause()
        play.value = exoplayer.playWhenReady
    }

    fun dispose() {
        exoplayer.removeListener(listener)
        exoplayer.release()
        mediaSession.release()
        Log.d(TAG, "release player $exoplayer")
    }

    fun seekFastReplay10() {
        exoplayer.seekBack()
    }

    fun seekFastForward10(){
        exoplayer.seekForward()
    }

    fun skipPrevious() {
        if(playerMediaState.value.hasPrevious()){

            playerMediaState.updateWith(
                currentPlaylistIndex = playerMediaState.value.getPreviousIndex(requestShuffle.value)
            )
            playerMediaState.value.getCurrentMedia()?.apply {
                loadMedia(this)
            }?:kotlin.run{ playerEvents.value = PlayerEvent.Error(java.lang.Exception("no previous video"))}
        }

    }

    fun skipNext() {

        if(playerMediaState.value.hasNext()){
            playerMediaState.updateWith(
                currentPlaylistIndex = playerMediaState.value.getNextIndex(requestShuffle.value)
            )
            playerMediaState.value.getCurrentMedia()?.apply {
                loadMedia(this)
            }?: kotlin.run{ playerEvents.value = PlayerEvent.Error(java.lang.Exception("no next video"))}
        }
    }

    fun userSeekIntent(seek: Float){
        playerState.updateWith(
            currentPositionPercent = seek,
            currentTimeFormatted = formatTime((playerState.value.duration * seek).toLong())
        )
        seekMode.value = true
    }


    fun seekToPosition() {
        val timeToSeek = (playerState.value.duration * playerState.value.currentPositionPercent).toLong()
        exoplayer.seekTo(timeToSeek)
        seekMode.value = false
    }

    fun changeVolume(){
        exoplayer.volume = if(exoplayer.volume > 0) 0f else 1f
    }


    fun changeShuffle(){
        requestShuffle.value = !requestShuffle.value
    }
    
    fun changeAutoplay() {
        requestAutoplay.value = !requestAutoplay.value
    }

    private fun formatTime(time : Long): String {
        return String.format(
            locale = Locale.getDefault(),
            format = "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(time),
            TimeUnit.MILLISECONDS.toSeconds(time) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(time)
                    )
        )
    }

    fun selectChapter(chapter: MediaChapter, timePattern: String = "HH:mm:ss") {
       try {
           val time = LocalTime.parse(chapter.timeFormatted, DateTimeFormatter.ofPattern(timePattern))
           val timeMs = time.get(ChronoField.MILLI_OF_DAY).toLong()
           exoplayer.seekTo(timeMs)
       }
       catch (ex : DateTimeParseException){
           Log.e(TAG,"The time provided in the chapter: $chapter do not respect the format $timePattern")
       }
       catch (ex : Exception){
           Log.e(TAG,"selectChapter exception : $ex")
       }
    }

    fun isPlayingTheFirstVideo() = playerMediaState.value.isPlayingTheFirstVideo()
    fun isPlayingTheLastVideo() = playerMediaState.value.isPlayingTheLastVideo()

}