package com.example.kunbaapp.ui.shared

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.example.kunbaapp.data.models.dto.NodeDto

@Composable
fun NodeItem(
    node: NodeDto,
    onItemClick: (Int) -> Unit,
    //toggleFavorite: (Int) -> Unit,
    //isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small)),
                //.clickable { onItemClick(node.nodeId) },
                verticalArrangement = Arrangement.Center
            ) {
                val fullName = "${node.firstName} ${node.lastName}"
                RootInformation(fullName)
                val placeOfBirth = "Birth Place: ${node.placeOfBirth}"
                RootInformation(placeOfBirth)
                val dateOfBirth = "DOB: ${node.dateOfBirth}"
                RootInformation(dateOfBirth)
                if (node.gender == 'M') {
                    RootInformation(name = "Male")
                } else {
                    RootInformation(name = "Female")
                }
                // Show Family Button if not null
                if(node.familyId != null) {
                    Button(onClick = { onItemClick(node.familyId) }) {
                        Text(text = "Family")
                    }
                }
            }
    }
}