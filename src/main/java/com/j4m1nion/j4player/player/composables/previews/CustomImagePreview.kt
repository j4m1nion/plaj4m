package com.j4m1nion.j4player.player.composables.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.j4m1nion.j4player.player.R
import com.j4m1nion.j4player.player.composables.views.player.CustomImageWithFixedSize

@Preview
@Composable
private fun CustomImageWithFixedSizePreview(){
    CustomImageWithFixedSize(
        modifier = Modifier,
        width = 300.dp,
        height = 300.dp,
        url = null,
        resourceRes = R.drawable.image_empty,
        errorRes = R.drawable.image_empty,
        contentDescription = "default content description"
    )
}