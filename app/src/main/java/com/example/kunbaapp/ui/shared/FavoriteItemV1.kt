package com.example.kunbaapp.ui.shared

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.utils.EntityType

@Composable
fun FavoriteItemV1(
    item: Favorite,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ){
            DogIcon(
                if(item.type == EntityType.Root)
                    R.drawable.root
                else if(item.type == EntityType.Node)
                R.drawable.node
                else
                R.drawable.family
            )
            DogInformation(item.displayText,item.addedOn)
        }
    }
}