package com.j4m1nion.j4player.player.composables.views.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.j4m1nion.j4player.player.R


@Composable
fun CustomImageWithFixedSize(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    url : String? = null,
    resourceRes: Int? = null,
    errorRes: Int = R.drawable.image_empty,
    contentDescription: String = ""
){
    when{
        url?.isNotBlank() == true -> AsyncImage(
            modifier = modifier
                .height(height)
                .width(width),
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            error = painterResource(resourceRes ?: errorRes),
            contentDescription = contentDescription
        )
        resourceRes != null -> Image(
            modifier = modifier
                .height(height)
                .width(width),
            painter = painterResource(resourceRes),
            contentScale = ContentScale.Crop,
            contentDescription = contentDescription,

            )
        else -> {
            Box(
                modifier = modifier
                    .width(height)
                    .height(width)
                    .background(MaterialTheme.colorScheme.secondary)
            )
        }
    }
}