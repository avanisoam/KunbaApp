package com.example.kunbaapp.ui.shared

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.R

@Composable
fun AvatarIcon(
    avatar: Painter,
    description: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = avatar,//painterResource(id = avatar),
        contentDescription = description,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(dimensionResource(R.dimen.fav_image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(RoundedCornerShape(10.dp)),
    )
}