package com.example.kunbaapp.ui.shared

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.models.dto.V2.RootRegisterDtoV2

@Composable
fun RootItem(
    root: RootRegisterDto,
    favoriteIds: List<Int>,
    onItemClick: (Int) -> Unit,
    toggleFavorite: (Int) -> Unit,
    //isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
                .clickable { onItemClick(root.rootId) },
            horizontalArrangement = Arrangement.Center
        ){
            RootInformation(root.rootName)
            Spacer(Modifier.weight(1f))
            Log.d("Toggle", root.rootId.toString())
            if(favoriteIds.contains(root.rootId))
            {
                FavoriteButton(
                    isFavorite = true ?: false ,
                    onClick = { toggleFavorite(root.rootId) }
                )
            }

        }
    }
}

@Composable
fun RootItemV2(
    root: RootRegisterDtoV2,
    favoriteIds: List<Int>,
    onItemClick: (Int) -> Unit,
    toggleFavorite: (Int) -> Unit,
    //isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
                .clickable { onItemClick(root.id) },
            horizontalArrangement = Arrangement.Center
        ){
            RootInformation(root.rootName?:"")
            Spacer(Modifier.weight(1f))
            Log.d("Toggle", root.id.toString())
            if(favoriteIds.contains(root.id))
            {
                FavoriteButton(
                    isFavorite = true ?: false ,
                    onClick = { toggleFavorite(root.id) }
                )
            }

        }
    }
}
