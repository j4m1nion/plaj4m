package com.j4m1nion.j4player.player

import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.DeviceInfo
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import androidx.media3.common.text.CueGroup
import kotlinx.coroutines.flow.MutableStateFlow

class PlayerListener : Player.Listener {

    val events = MutableStateFlow<PlayerEvent>(PlayerEvent.Idle)

    companion object{
        const val TAG = "Player.Listener"
    }


    override fun onEvents(player: Player, events: Player.Events) {
        super.onEvents(player, events)
        Log.d(TAG, "onEvents: $events")
        this.events.value = PlayerEvent.Events(events)
    }

    override fun onTimelineChanged(timeline: Timeline, reason: Int) {
        super.onTimelineChanged(timeline, reason)
        Log.d(TAG, "onTimelineChanged: $timeline for $reason")
        events.value = PlayerEvent.TimeLineChanged(timeline, reason)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        Log.d(TAG, "onMediaItemTransition: $mediaItem for $reason")
        events.value = PlayerEvent.MediaItemTransitioned(mediaItem, reason)
    }

    override fun onTracksChanged(tracks: Tracks) {
        super.onTracksChanged(tracks)
        Log.d(TAG, "onTracksChanged: $tracks")
        events.value = PlayerEvent.TracksChanged(tracks)
    }

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onMediaMetadataChanged(mediaMetadata)
        Log.d(TAG, "onMediaMetadataChanged: $mediaMetadata")
        events.value = PlayerEvent.MediaMetadataChanged(mediaMetadata)
    }

    override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onPlaylistMetadataChanged(mediaMetadata)
        Log.d(TAG, "onPlaylistMetadataChanged: $mediaMetadata")
        events.value = PlayerEvent.PlaylistMetadataChanged(mediaMetadata)
    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
        super.onIsLoadingChanged(isLoading)
        Log.d(TAG, "onIsLoadingChanged: $isLoading")
        events.value = PlayerEvent.IsLoading(isLoading)
    }


    override fun onAvailableCommandsChanged(availableCommands: Player.Commands) {
        super.onAvailableCommandsChanged(availableCommands)
        Log.d(TAG, "onAvailableCommandsChanged: $availableCommands")
        events.value = PlayerEvent.AvailableCommandsChanged(availableCommands)
    }

    override fun onTrackSelectionParametersChanged(parameters: TrackSelectionParameters) {
        super.onTrackSelectionParametersChanged(parameters)
        Log.d(TAG, "onTrackSelectionParametersChanged: $parameters")
        events.value = PlayerEvent.TrackSelectionParametersChanged(parameters)
    }


    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        Log.d(TAG, "onPlaybackStateChanged: $playbackState")
        events.value = when(playbackState){
            Player.STATE_IDLE -> PlayerEvent.Idle
            Player.STATE_BUFFERING -> PlayerEvent.Buffering
            Player.STATE_READY -> PlayerEvent.Ready
            Player.STATE_ENDED -> PlayerEvent.Ended
            else -> PlayerEvent.Idle
        }
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        Log.d(TAG, "onPlayWhenReadyChanged: $playWhenReady")
        events.value = if(playWhenReady) PlayerEvent.PlayWhenReady(reason)
                        else PlayerEvent.NotPlayWhenReady(reason)
    }

    override fun onPlaybackSuppressionReasonChanged(playbackSuppressionReason: Int) {
        super.onPlaybackSuppressionReasonChanged(playbackSuppressionReason)
        Log.d(TAG, "onPlaybackSuppressionReasonChanged: $playbackSuppressionReason")
        events.value = PlayerEvent.PlaybackSuppressionReasonChanged(playbackSuppressionReason)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        Log.d(TAG, "onIsPlayingChanged: $isPlaying")
        events.value = if(isPlaying){
            PlayerEvent.Play
        }
        else{
            PlayerEvent.Pause
        }
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        super.onRepeatModeChanged(repeatMode)
        Log.d(TAG, "onRepeatModeChanged: $repeatMode")
        events.value = PlayerEvent.RepeatModeChanged(repeatMode)
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        super.onShuffleModeEnabledChanged(shuffleModeEnabled)
        Log.d(TAG, "onShuffleModeEnabledChanged: $shuffleModeEnabled")
        events.value = PlayerEvent.ShuffleModeChanged(shuffleModeEnabled)
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        Log.d(TAG, "onPlayerError: $error")
        events.value = PlayerEvent.Error(error)
    }

    override fun onPlayerErrorChanged(error: PlaybackException?) {
        super.onPlayerErrorChanged(error)
        Log.d(TAG, "onPlayerErrorChanged: $error")
        events.value = PlayerEvent.ErrorChanged(error)
    }

    override fun onPositionDiscontinuity(
        oldPosition: Player.PositionInfo,
        newPosition: Player.PositionInfo,
        reason: Int
    ) {
        super.onPositionDiscontinuity(oldPosition, newPosition, reason)
        Log.d(TAG, "onPositionDiscontinuity: from $oldPosition to $newPosition for $reason")
        events.value = PlayerEvent.PositionDiscontinued(oldPosition, newPosition, reason)
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
        super.onPlaybackParametersChanged(playbackParameters)
        Log.d(TAG, "onPlaybackParametersChanged: $playbackParameters")
        events.value = PlayerEvent.PlaybackParametersChanged(playbackParameters)
    }

    override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
        super.onSeekBackIncrementChanged(seekBackIncrementMs)
        Log.d(TAG, "onSeekBackIncrementChanged: $seekBackIncrementMs")
        events.value = PlayerEvent.SeekBack(seekBackIncrementMs)
    }

    override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
        super.onSeekForwardIncrementChanged(seekForwardIncrementMs)
        Log.d(TAG, "onSeekForwardIncrementChanged: $seekForwardIncrementMs")
        events.value = PlayerEvent.SeekForward(seekForwardIncrementMs)
    }

    override fun onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs: Long) {
        super.onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs)
        Log.d(TAG, "onMaxSeekToPreviousPositionChanged: $maxSeekToPreviousPositionMs")
        events.value = PlayerEvent.MaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs)
    }


    override fun onAudioAttributesChanged(audioAttributes: AudioAttributes) {
        super.onAudioAttributesChanged(audioAttributes)
        Log.d(TAG, "onAudioAttributesChanged: $audioAttributes")
        events.value = PlayerEvent.AudioAttributesChanged(audioAttributes)
    }

    override fun onVolumeChanged(volume: Float) {
        super.onVolumeChanged(volume)
        Log.d(TAG, "onVolumeChanged: $volume")
        events.value = PlayerEvent.VolumeChanged(volume)
    }

    override fun onSkipSilenceEnabledChanged(skipSilenceEnabled: Boolean) {
        super.onSkipSilenceEnabledChanged(skipSilenceEnabled)
        Log.d(TAG, "onSkipSilenceEnabledChanged: $skipSilenceEnabled")
        events.value = PlayerEvent.SkipSilenceEnabledChanged(skipSilenceEnabled)
    }

    override fun onDeviceInfoChanged(deviceInfo: DeviceInfo) {
        super.onDeviceInfoChanged(deviceInfo)
        Log.d(TAG, "onDeviceInfoChanged: $deviceInfo")
        events.value = PlayerEvent.DeviceInfoChanged(deviceInfo)
    }

    override fun onDeviceVolumeChanged(volume: Int, muted: Boolean) {
        super.onDeviceVolumeChanged(volume, muted)
        Log.d(TAG, "onDeviceVolumeChanged: $volume and muted: $muted")
        events.value = PlayerEvent.DeviceVolumeChanged(volume, muted)
    }

    override fun onVideoSizeChanged(videoSize: VideoSize) {
        super.onVideoSizeChanged(videoSize)
        Log.d(TAG, "onVideoSizeChanged: $videoSize")
        events.value = PlayerEvent.VideoSizeChanged(videoSize)

    }

    override fun onSurfaceSizeChanged(width: Int, height: Int) {
        super.onSurfaceSizeChanged(width, height)
        Log.d(TAG, "onSurfaceSizeChanged: [$width x $height]")
        events.value = PlayerEvent.SurfaceSizeChanged(width, height)
    }

    override fun onRenderedFirstFrame() {
        super.onRenderedFirstFrame()
        Log.d(TAG, "onRenderedFirstFrame")
        events.value = PlayerEvent.RenderedFirstFrame
    }


    override fun onCues(cueGroup: CueGroup) {
        super.onCues(cueGroup)
        Log.d(TAG, "onCues: $cueGroup")
        events.value = PlayerEvent.Cues(cueGroup)
    }

}