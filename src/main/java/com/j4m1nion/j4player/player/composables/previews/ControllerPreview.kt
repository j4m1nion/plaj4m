package com.j4m1nion.j4player.player.composables.previews

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.j4m1nion.j4player.player.composables.views.player.DrawControllerPlayPauseSkipsButtons
import com.j4m1nion.j4player.player.composables.views.player.DrawControllerSeekBars
import com.j4m1nion.j4player.player.composables.views.player.DrawControllerTitleAndTopControls
import com.j4m1nion.j4player.player.composables.views.player.DrawMediaInfos
import com.j4m1nion.j4player.player.configuration.PlayerControllerConfiguration
import com.j4m1nion.j4player.player.configuration.PlayerFeatureMediaInfos
import com.j4m1nion.j4player.player.configuration.PlayerViewConfiguration
import com.j4m1nion.j4player.player.configuration.PlayerViewTopConfiguration
import com.j4m1nion.j4player.player.model.MediaFile
import com.j4m1nion.j4player.player.theme.PlayerTheme

@Preview(showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=portrait")
@Composable
private fun PreviewPlayerControlsPortrait(){
    PlayerTheme {
        Column {
            Box(
                Modifier.fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.4f))
            ) {
                DrawControllerPlayPauseSkipsButtons(
                    Modifier.align(Alignment.Center),
                    PlayerViewConfiguration(),
                    ORIENTATION_PORTRAIT,
                    null,
                    false)

                //TOP
                DrawControllerTitleAndTopControls(
                    Modifier.align(Alignment.TopCenter),
                    configuration = PlayerViewTopConfiguration(
                        showTitleInPortrait = true,
                        showTitleInLandscape = true),
                    MediaFile(
                        "",
                        null,
                        "",
                        null,
                        "Title",
                        "Description"
                    ),
                    ORIENTATION_PORTRAIT

                )

                //BOTTOM
                DrawControllerSeekBars(
                    Modifier.align(Alignment.BottomCenter),
                    0.6f,
                    0.3f,
                    null,
                    "05:15",
                    "27:24",
                    configuration = PlayerControllerConfiguration(),
                    volume = true
                )
            }
            DrawMediaInfos(
                modifier = Modifier,
                playerViewModel = null,
                configuration = PlayerFeatureMediaInfos(),
                shuffle = false,
                autoplay = false,
                mediaFile = MediaFile(
                    "",
                    null,
                    "",
                    null,
                    "This is the title of the video",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum nisl mauris, tempus sed lacus nec, porttitor fringilla odio. Aenean convallis dui in malesuada scelerisque. Cras auctor pulvinar pharetra. Curabitur quis neque vitae libero rutrum ultricies. In est nibh, dapibus in lectus ut, dapibus luctus magna. Fusce in aliquet nunc. Praesent elit metus, condimentum eget lorem eu, rhoncus ullamcorper massa. Donec sodales, leo at fringilla euismod, nisl turpis dapibus elit, id mattis est sapien bibendum libero. Donec interdum purus a vestibulum elementum. Morbi tellus orci, auctor ac nunc nec, vehicula convallis nunc. Integer mattis euismod nulla ac blandit. Ut eu libero sed risus ullamcorper consequat eget sed tellus. Fusce facilisis molestie leo vitae fringilla."
                ),
                expanded = true,
                playlist = true,
                onChapterSelected = {}
            )
        }
    }
}

@Preview(showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape")
@Composable
private fun PreviewPlayerControlsLandscape(){
    PlayerTheme {
        Column {
            Box(
                Modifier.fillMaxWidth()
                    .height(891.dp)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.4f))
            ) {
                DrawControllerPlayPauseSkipsButtons(
                    Modifier.align(Alignment.Center),
                    PlayerViewConfiguration(),
                    ORIENTATION_LANDSCAPE,
                    null,
                    false)

                //TOP
                DrawControllerTitleAndTopControls(
                    Modifier.align(Alignment.TopCenter),
                    configuration = PlayerViewTopConfiguration(
                        showTitleInPortrait = true,
                        showTitleInLandscape = true,
                        ),
                    MediaFile(
                        "",
                        null,
                        "",
                        null,
                        "Title",
                        "Description"
                    ),
                    ORIENTATION_LANDSCAPE

                )

                //BOTTOM
                DrawControllerSeekBars(
                    Modifier.align(Alignment.BottomCenter),
                    0.6f,
                    0.3f,
                    null,
                    "05:15",
                    "27:24",
                    PlayerControllerConfiguration(),
                    volume = true
                )
            }

        }

    }
}
