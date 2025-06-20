package com.j4m1nion.j4player.player.composables.views.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size

@Composable
fun PlayPauseButton(
    modifier: Modifier,
    onClickAction: () -> Unit,
    play: Boolean,
    playIcon: Int,
    pauseIcon: Int,
    playDesc: Int,
    pauseDesc: Int
    ){
    IconButton(
        modifier = modifier.size(80.dp),
        onClick = { onClickAction.invoke() }) {
        if (play) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(pauseIcon),
                contentDescription = stringResource(pauseDesc),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        } else {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(playIcon),
                contentDescription = stringResource(playDesc),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }
    }
}

@Composable
fun PlayerOptionButton(
    modifier: Modifier,
    visibility : Boolean,
    onClickAction: () -> Unit,
    icon: Int,
    contentDescription: Int
) {

    IconButton(
    modifier = modifier,
    enabled = visibility,
    onClick = {
        onClickAction.invoke()
    }
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(icon),
            contentDescription = stringResource(contentDescription),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}