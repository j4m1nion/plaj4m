package com.j4m1nion.j4player.player.composables.previews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.j4m1nion.j4player.player.R
import com.j4m1nion.j4player.player.composables.views.player.DrawMediaFileList
import com.j4m1nion.j4player.player.composables.views.player.DrawMediaInfos
import com.j4m1nion.j4player.player.configuration.PlayerFeatureMediaInfos
import com.j4m1nion.j4player.player.model.MediaFile
import com.j4m1nion.j4player.player.theme.PlayerTheme

@Preview(showSystemUi = true)
@Composable
private fun PlayerMockPreview(){

    PlayerTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.background)) {
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
                    null,
                    null,
                    "Title",
                    "Description"
                ),
                shuffle = true,
                autoplay = true,
                playlist = true,
                configuration = PlayerFeatureMediaInfos(),
                expanded = true,
                playerViewModel = null,
                onChapterSelected = {}
            )
            val playlist = listOf(
                MediaFile("",null,"",null,"Title1","Praesent pellentesque velit non nisi consectetur, et malesuada mi porta. Suspendisse potenti. Nunc pharetra suscipit ligula venenatis ornare. Cras sed erat eu massa lobortis aliquet. Nam eget sapien eu odio aliquet rutrum in in dolor. Sed vitae dictum velit. Aliquam pharetra eros ut massa scelerisque placerat. Mauris sit amet scelerisque risus. Proin bibendum, mauris non pharetra faucibus, nunc dui sollicitudin ipsum, eget sollicitudin lacus massa eu justo. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam sed massa gravida, iaculis ipsum sed, iaculis lectus."),
                MediaFile("",null,"", R.drawable.image_empty,"Title2","Praesent pellentesque velit non nisi consectetur, et malesuada mi porta. Suspendisse potenti. Nunc pharetra suscipit ligula venenatis ornare. Cras sed erat eu massa lobortis aliquet. Nam eget sapien eu odio aliquet rutrum in in dolor. Sed vitae dictum velit. Aliquam pharetra eros ut massa scelerisque placerat. Mauris sit amet scelerisque risus. Proin bibendum, mauris non pharetra faucibus, nunc dui sollicitudin ipsum, eget sollicitudin lacus massa eu justo. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam sed massa gravida, iaculis ipsum sed, iaculis lectus."),
                MediaFile("",null,"",null,"Title3","Praesent pellentesque velit non nisi consectetur, et malesuada mi porta. Suspendisse potenti. Nunc pharetra suscipit ligula venenatis ornare. Cras sed erat eu massa lobortis aliquet. Nam eget sapien eu odio aliquet rutrum in in dolor. Sed vitae dictum velit. Aliquam pharetra eros ut massa scelerisque placerat. Mauris sit amet scelerisque risus. Proin bibendum, mauris non pharetra faucibus, nunc dui sollicitudin ipsum, eget sollicitudin lacus massa eu justo. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam sed massa gravida, iaculis ipsum sed, iaculis lectus.")
            )

            DrawMediaFileList(
                playlist,
                playlist[0],
                null,
                false
            )
        }
    }
}