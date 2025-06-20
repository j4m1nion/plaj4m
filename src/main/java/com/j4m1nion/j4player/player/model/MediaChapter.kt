package com.j4m1nion.j4player.player.model

import androidx.annotation.Keep

@Keep
data class MediaChapter(
    val thumbnailUrl: String? = null,
    val thumbnailRes: Int? = null,
    val timeFormatted: String,
    val chapterTitle: String? = null
)