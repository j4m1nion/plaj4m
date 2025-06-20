package com.j4m1nion.j4player.player.model

import androidx.annotation.Keep

@Keep
data class MediaFile(
    val mediaUrl: String,
    val licenseUrl : String? = null,
    val thumbnailUrl: String? = null,
    val thumbnailRes: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val chapters: List<MediaChapter> = emptyList()
)