package com.example.kunbaapp.ui.shared

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto

@Composable
fun RootNodeItem(
    node: NodeDto,
    onItemClick: (Int) -> Unit,
    avatar: Painter,
    description: String,
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
            horizontalArrangement = Arrangement.Center
        ){
            RootInformation(node.firstName)
            Spacer(modifier = Modifier.width(20.dp))
            AvatarIcon(avatar = avatar, description = description)
        }
    }
}
