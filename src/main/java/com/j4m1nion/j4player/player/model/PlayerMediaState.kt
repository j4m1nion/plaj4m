package com.j4m1nion.j4player.player.model

import androidx.compose.runtime.MutableState
import kotlin.random.Random

data class PlayerMediaState(
    val playlist : List<MediaFile> = emptyList(),
    val currentMediaFile : MediaFile? = null,
    val currentPlaylistIndex : Int = 0
){
    fun getCurrentMedia(): MediaFile? {
        return playlist.getOrNull(currentPlaylistIndex)
    }

    fun getPreviousIndex(shuffle : Boolean): Int {
        return currentPlaylistIndex - if(shuffle){
            Random.nextInt(1, currentPlaylistIndex + 1)
        }
        else 1
    }

    fun getNextIndex(shuffle: Boolean): Int{
        return currentPlaylistIndex + if(shuffle){
            Random.nextInt(1, playlist.size - currentPlaylistIndex)
        }
        else 1
    }

    fun isPlayingTheFirstVideo() = currentPlaylistIndex == 0
    fun isPlayingTheLastVideo() = currentPlaylistIndex == playlist.size -1

    fun hasPrevious() = currentPlaylistIndex > 0
    fun hasNext() = currentPlaylistIndex < playlist.size - 1
}

fun MutableState<PlayerMediaState>.updateWith(
    playlist: List<MediaFile> ? = null,
    currentMediaFile : MediaFile? = null,
    currentPlaylistIndex : Int? = null
){
    value = value.copy(
        playlist = playlist ?: value.playlist ,
        currentMediaFile = currentMediaFile ?: value.currentMediaFile,
        currentPlaylistIndex = currentPlaylistIndex ?: value.currentPlaylistIndex


    )
}