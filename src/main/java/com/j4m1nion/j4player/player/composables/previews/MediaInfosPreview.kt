package com.j4m1nion.j4player.player.composables.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.j4m1nion.j4player.player.R
import com.j4m1nion.j4player.player.composables.views.player.DrawMediaInfos
import com.j4m1nion.j4player.player.configuration.PlayerFeatureMediaInfos
import com.j4m1nion.j4player.player.model.MediaChapter
import com.j4m1nion.j4player.player.model.MediaFile
import com.j4m1nion.j4player.player.theme.PlayerTheme

@Preview(showSystemUi = true)
@Composable
private fun PreviewMediaInfos(){

    PlayerTheme {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Gray)
            ){
                Text("PLAYER", modifier = Modifier.align(Alignment.Center))
            }

            DrawMediaInfos(
                modifier = Modifier,
                mediaFile = MediaFile(
                    "",
                    null,
                    "",
                    null,
                    "Title",
                    "Integer ullamcorper dapibus euismod. Curabitur viverra, magna eget mollis dictum, orci est hendrerit lectus, non consectetur ante justo et lorem. Nulla finibus facilisis massa vitae consequat. Praesent lacinia convallis magna, dictum sodales ipsum eleifend non. ",
                    chapters = listOf(
                        MediaChapter(
                            thumbnailUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png",
                            thumbnailRes = null,
                            timeFormatted = "00:00:00",
                            chapterTitle = "Intro"
                        ),
                        MediaChapter(
                            thumbnailUrl = null,
                            thumbnailRes = R.drawable.image_empty,
                            timeFormatted = "00:03:23",
                            chapterTitle = "Chapter 1"
                        ),
                        MediaChapter(
                            thumbnailUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2b/Big.Buck.Bunny.-.Frank.Rinky.Gimera.png",
                            thumbnailRes = null,
                            timeFormatted = "00:05:41",
                            chapterTitle = "Chapter 2"
                        ),
                        MediaChapter(
                            thumbnailUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png",
                            thumbnailRes = null,
                            timeFormatted = "00:06:00",
                            chapterTitle = "A New Home"
                        ),
                        MediaChapter(
                            thumbnailUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png",
                            thumbnailRes = null,
                            timeFormatted = "00:06:11",
                            chapterTitle = "Chapter 3"
                        ),
                        MediaChapter(
                            thumbnailUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a7/Big_Buck_Bunny_thumbnail_vlc.png",
                            thumbnailRes = null,
                            timeFormatted = "00:08:25",
                            chapterTitle = "Ending"
                        ),

                        )

                ),
                shuffle = true,
                autoplay = true,
                playlist = true,
                configuration = PlayerFeatureMediaInfos(),
                expanded = true,
                playerViewModel = null,
                onChapterSelected = null
            )
        }
    }


}