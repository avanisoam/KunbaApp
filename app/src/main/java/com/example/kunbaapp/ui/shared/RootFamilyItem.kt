package com.example.kunbaapp.ui.shared

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.models.dto.V2.FamilyDtoV2
import com.example.kunbaapp.data.models.dto.V2.FamilyWithChildrenDto

@Composable
fun RootFamilyItem(
    family: FamilyDtoV2,//FamilyDto,
    onItemClick: (Int) -> Unit,
    onIndividualClick: (Int) -> Unit,
    //toggleFavorite: (Int) -> Unit,
    //isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small)),
                //.clickable { onItemClick(family.familyId) },
            horizontalArrangement = Arrangement.Center
        ) {
            if (family.fatherInfo != null) {
                Button(onClick = { onIndividualClick(family.fatherInfo.nodeId) }) {
                    Text(text = family.fatherInfo.firstName)
                }
            }
            Button(onClick = { onItemClick(family.familyId) }) {
                Text(text = "M")
            }
            if (family.motherInfo != null) {
                Button(onClick = { onIndividualClick(family.motherInfo.nodeId) }) {
                    Text(text = family.motherInfo.firstName)
                }
            }
        }
        }
}

@Composable
fun FamilyItem(
    family: FamilyWithChildrenDto,//FamilyDto,
    onItemClick: (Int) -> Unit,
    onIndividualClick: (Int) -> Unit,
    //toggleFavorite: (Int) -> Unit,
    //isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small)),
            //.clickable { onItemClick(family.familyId) },
            horizontalArrangement = Arrangement.Center
        ) {
            if (family.fatherNode != null) {
                Button(onClick = { onIndividualClick(family.fatherNode.nodeId) }) {
                    Text(text = family.fatherNode.firstName)
                }
            }
            Button(onClick = { onItemClick(family.familyId) }) {
                Text(text = "M")
            }
            if (family.motherNode != null) {
                Button(onClick = { onIndividualClick(family.motherNode.nodeId) }) {
                    Text(text = family.motherNode.firstName)
                }
            }
        }
    }
}
