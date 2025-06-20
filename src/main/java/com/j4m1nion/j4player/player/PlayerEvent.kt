package com.j4m1nion.j4player.player

import androidx.media3.common.AudioAttributes
import androidx.media3.common.DeviceInfo
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Player.PositionInfo
import androidx.media3.common.Timeline
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import androidx.media3.common.text.CueGroup


sealed class PlayerEvent{
    data object Idle : PlayerEvent()
    data object Ready : PlayerEvent()
    data object Ended : PlayerEvent()
    data object Buffering : PlayerEvent()
    data object Play : PlayerEvent()
    data object Pause : PlayerEvent()
    data class Error(val error: Exception) : PlayerEvent()
    data class ErrorChanged(val error: Exception?) : PlayerEvent()
    data class Events(val events: Player.Events) : PlayerEvent()
    data class TimeLineChanged(val timeline: Timeline, val reason : Int) : PlayerEvent()
    data class MediaItemTransitioned(val mediaItem : MediaItem?, val reason : Int) : PlayerEvent()
    data class TracksChanged(val tracks: Tracks) : PlayerEvent()
    data class MediaMetadataChanged(val mediaMetadata : MediaMetadata) : PlayerEvent()
    data class PlaylistMetadataChanged(val mediaMetadata: MediaMetadata) : PlayerEvent()
    data class IsLoading(val loading : Boolean) : PlayerEvent()
    data class AvailableCommandsChanged(val availableCommands : Player.Commands) : PlayerEvent()
    data class TrackSelectionParametersChanged(val parameters: TrackSelectionParameters) : PlayerEvent()
    data class PlayWhenReady(val reason : Int) : PlayerEvent()
    data class NotPlayWhenReady(val reason: Int) : PlayerEvent()
    data class PlaybackSuppressionReasonChanged(val playbackSuppressionReason : Int) : PlayerEvent()
    data class RepeatModeChanged(val repeat : Int) : PlayerEvent()
    data class ShuffleModeChanged(val shuffle: Boolean) : PlayerEvent()
    data class PositionDiscontinued(val oldPosition: PositionInfo,
        val newPosition: PositionInfo,
        val reason : Int) : PlayerEvent()
    data class PlaybackParametersChanged(val playbackParameters: PlaybackParameters) : PlayerEvent()
    data class SeekBack(val seekBackMs : Long) : PlayerEvent()
    data class SeekForward(val seekForwardMs: Long) : PlayerEvent()
    data class MaxSeekToPreviousPositionChanged(val maxSeekToPreviousPositionMs : Long) : PlayerEvent()
    data class AudioAttributesChanged(val audioAttributes: AudioAttributes) : PlayerEvent()
    data class VolumeChanged(val volumeChanged: Float) : PlayerEvent()
    data class SkipSilenceEnabledChanged(val skipSilence: Boolean) : PlayerEvent()
    data class DeviceInfoChanged(val deviceInfo : DeviceInfo) : PlayerEvent()
    data class DeviceVolumeChanged(val volume: Int, val muted: Boolean) : PlayerEvent()
    data class VideoSizeChanged(val videoSize: VideoSize) : PlayerEvent()
    data class SurfaceSizeChanged(val width: Int, val height: Int) : PlayerEvent()
    data object RenderedFirstFrame : PlayerEvent()
    data class Cues(val cueGroup: CueGroup) : PlayerEvent()
}