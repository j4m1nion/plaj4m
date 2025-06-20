package com.j4m1nion.j4player.player.model.mapper

import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.DrmConfiguration
import com.j4m1nion.j4player.player.model.MediaFile

fun MediaFile.buildMediaItem(): MediaItem {
    return MediaItem.Builder()
        .setUri(mediaUrl)
        .setDrmConfiguration(
            if(licenseUrl?.isNotBlank() == true){
                DrmConfiguration.Builder(C.WIDEVINE_UUID)
                    .setLicenseUri(licenseUrl)
                    .setMultiSession(true)
                    .build()
            }
            else{
                null
            }
        ).build()
}