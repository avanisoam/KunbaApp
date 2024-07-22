package com.example.kunbaapp.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.NodeDto

@Composable
fun FavoriteItem(
    node: NodeDto,
    avatar: Painter,
    description: String,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
                .clickable { onItemClick(node.nodeId) },
        ){
            val fullName = "${node.firstName} ${node.lastName}"
            AvatarIcon(avatar = avatar, description = description)
            RootInformation(fullName)
        }
    }
}