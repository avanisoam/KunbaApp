package com.example.kunbaapp.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto

@Composable
fun RootFamilyItem(
    family: FamilyDto,
    onItemClick: (Int) -> Unit,
    onIndividualClick: (Int) -> Unit,
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
        ){
            if(family.fatherInfo != null) {
                Button(onClick = { onIndividualClick(family.fatherInfo.nodeId) }) {
                    Text(text = family.fatherInfo.firstName)
                }
            }
            Button(onClick = { onItemClick(family.familyId) }) {
                Text(text = "M")
            }
            if(family.motherInfo != null) {
                Button(onClick = { onIndividualClick(family.motherInfo.nodeId) }) {
                    Text(text = family.motherInfo.firstName)
                }
            }
            }
        }
}
