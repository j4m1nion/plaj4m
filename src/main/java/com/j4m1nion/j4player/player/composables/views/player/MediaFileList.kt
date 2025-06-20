package com.j4m1nion.j4player.player.composables.views.player

import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.j4m1nion.j4player.player.PlayerViewModel
import com.j4m1nion.j4player.player.R
import com.j4m1nion.j4player.player.model.MediaFile

@OptIn(UnstableApi::class)
@Composable
fun DrawMediaFileList(
    playlist: List<MediaFile>,
    currentMedia: MediaFile?,
    playerViewModel: PlayerViewModel?,
    playerError: Boolean
){
    val listState = rememberLazyListState()

    LaunchedEffect(currentMedia) {
        val index = playlist.indexOf(currentMedia)
        if(index >= 0){
            listState.animateScrollToItem(index)
        }

    }

    LazyColumn(
         userScrollEnabled = true,
        state = listState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    ) {
        items(playlist){
            AnimatedDrawMediaFile(it, currentMedia, playerError){ mediaFile ->
                playerViewModel?.loadMediaFromList(mediaFile)
            }
        }
    }
}

@Composable
private fun AnimatedDrawMediaFile(
    mediaFile: MediaFile,
    currentPlayingMediaFile: MediaFile?,
    playerError :Boolean,
    onPlay: ((MediaFile) -> Unit) ? = null
){
    var expanded by rememberSaveable { mutableStateOf(false) }
  
    LaunchedEffect(currentPlayingMediaFile) {
        if(mediaFile == currentPlayingMediaFile){
            expanded = true
        }
    }
  
    AnimatedContent(
        targetState = expanded,
        transitionSpec = {
            fadeIn().togetherWith(
                fadeOut(
                    animationSpec = tween(
                        200
                    )
                )
            )
        }, label = "TitleAnimation"
    ) { targetState ->
        DrawMediaFile(Modifier
            .clickable { expanded = !expanded }, mediaFile, currentPlayingMediaFile, targetState, playerError, onPlay)
    }
}

@Composable
private fun DrawMediaFile(
    modifier: Modifier,
    mediaFile: MediaFile,
    currentPlayingMediaFile: MediaFile?,
    expanded: Boolean,
    playerError: Boolean,
    onPlay: ((MediaFile) -> Unit) ? = null
){
    Column(modifier) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp)) {
            Box {
                CustomImageWithFixedSize(
                    modifier = Modifier,
                    width = 150.dp,
                    height = 100.dp,
                    url = mediaFile.thumbnailUrl,
                    resourceRes = mediaFile.thumbnailRes,
                    errorRes = R.drawable.image_empty,
                    contentDescription = mediaFile.title ?: ""
                )
                if(currentPlayingMediaFile != mediaFile || playerError){
                    Box(modifier = Modifier
                        .matchParentSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f)))
                    Image(
                        painter = painterResource(R.drawable.ic_play),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        contentDescription = stringResource(R.string.button_play_cd),
                        modifier = Modifier
                            .height(60.dp)
                            .width(60.dp)
                            .align(Alignment.Center)
                            .clickable { onPlay?.invoke(mediaFile) }
                    )
                }


            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = mediaFile.title ?: "", style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary)

            }


        }
        if(expanded){
            Text(
                modifier = Modifier.padding(top = 4.dp, start = 8.dp).fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                text = mediaFile.description ?: "",
                color = MaterialTheme.colorScheme.secondary)
        }
    }
}


