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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.j4m1nion.j4player.player.PlayerViewModel
import com.j4m1nion.j4player.player.R
import com.j4m1nion.j4player.player.configuration.PlayerFeatureMediaInfos
import com.j4m1nion.j4player.player.model.MediaChapter
import com.j4m1nion.j4player.player.model.MediaFile


@OptIn(UnstableApi::class)
@Composable
fun DrawMediaInfos(
    modifier: Modifier,
    playerViewModel: PlayerViewModel?,
    configuration: PlayerFeatureMediaInfos,
    playlist: Boolean,
    shuffle: Boolean,
    autoplay: Boolean,
    mediaFile: MediaFile?,
    expanded : Boolean,
    onChapterSelected: ((MediaChapter) -> Unit)?
){
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
        DrawTitleRowAndDescription(modifier, mediaFile, playerViewModel, configuration, playlist,  shuffle, autoplay, targetState, onChapterSelected)
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun DrawTitleRowAndDescription(
    modifier: Modifier,
    mediaFile: MediaFile?,
    playerViewModel: PlayerViewModel?,
    configuration: PlayerFeatureMediaInfos,
    playlist: Boolean,
    shuffle: Boolean,
    autoplay: Boolean,
    expanded: Boolean,
    onChapterSelected: ((MediaChapter) -> Unit)?
) {
    Column(modifier.background(MaterialTheme.colorScheme.background).padding(bottom = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if(configuration.title) Arrangement.SpaceBetween else Arrangement.End
        ) {
                if(configuration.title){
                    Text(
                        mediaFile?.title ?: "",
                        modifier = Modifier.weight(1F, false),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Row {
                    if(configuration.shuffle && playlist){
                        Image(
                            modifier = Modifier.clickable { playerViewModel?.changeShuffle() },
                            painter = painterResource(R.drawable.ic_shuffle),
                            contentDescription = "",
                            colorFilter =
                            if (shuffle) ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            else ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                        )
                    }
                    if(configuration.autoplay && playlist){
                        Image(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable { playerViewModel?.changeAutoplay() },
                            painter = painterResource(R.drawable.ic_autoplay),
                            contentDescription = "",
                            colorFilter =
                            if (autoplay) ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            else ColorFilter.tint(MaterialTheme.colorScheme.secondary)

                        )
                    }
                }
        }
        if (configuration.description && expanded && mediaFile?.description?.isNotBlank() == true) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = mediaFile.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        if(configuration.chapters && mediaFile?.chapters?.isNotEmpty() == true && expanded){

            LazyRow(modifier = Modifier.padding(start = 16.dp, top = 10.dp)) {
                items(mediaFile.chapters){
                    DrawVideoChapter(
                        modifier = Modifier.padding(end = 8.dp).clickable { onChapterSelected?.invoke(it) },
                        it.thumbnailUrl,
                        it.thumbnailRes,
                        it.timeFormatted,
                        it.chapterTitle
                    )
                }
            }
        }



    }
}

@Composable
private fun DrawVideoChapter(
    modifier: Modifier,
    thumbnailUrl: String? = null,
    thumbnailRes: Int? = null,
    timeFormatted: String,
    chapterTitle: String? = null
){
    Column(modifier) {
        Box(Modifier) {
            CustomImageWithFixedSize(
                modifier = Modifier.clip(MaterialTheme.shapes.large),
                width = 150.dp,
                height = 100.dp,
                url = thumbnailUrl,
                resourceRes = thumbnailRes,
                errorRes = R.drawable.image_empty,
                contentDescription = chapterTitle ?: ""
            )
            Text(
                timeFormatted,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .width(150.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.0f),
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 1f)
                            )
                        )
                    ).align(Alignment.BottomStart)
            )
        }
        if(chapterTitle?.isNotBlank() == true){
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text= chapterTitle,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

