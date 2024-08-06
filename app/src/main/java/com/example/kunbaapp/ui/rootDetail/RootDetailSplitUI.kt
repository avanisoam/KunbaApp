package com.example.kunbaapp.ui.rootDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.V2.FamilyDtoV2
import com.example.kunbaapp.data.models.dto.V2.FamilyWithChildrenDto
import com.example.kunbaapp.ui.shared.DogItem
import com.example.kunbaapp.ui.shared.RootFamilyItem


@Composable
fun RootDetailSplitUI(
    families: List<FamilyDtoV2>?,//List<FamilyDto>,
    onItemClick: (Int) -> Unit,
    onIndividualClick: (Int) -> Unit,
    nodes: List<NodeDto>,
    modifier: Modifier = Modifier
    ) {

    Column(modifier = modifier) {
        RootDetailBody(
            families = families,
            onItemClick = onItemClick,
            onIndividualClick = onIndividualClick,
            modifier = Modifier.weight(1f),
        )
        Divider()
        NodesBody(
            nodes = nodes,
            onIndividualClick = onIndividualClick,
            modifier = Modifier.weight(1f),
        )
    }

}

@Composable
fun RootDetailBody(
    families: List<FamilyDtoV2>?,//List<FamilyDto>,
    onItemClick: (Int) -> Unit,
    onIndividualClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            families?.forEach { root ->
                RootFamilyItem(
                    family = root,
                    onItemClick = {onItemClick(root.familyId)},
                    onIndividualClick = {onIndividualClick(it)},
                )

            }
        }
    }
}

@Composable
fun NodesBody(
    nodes: List<NodeDto>?,
    onIndividualClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            nodes?.forEach { node ->
                /*
                RootNodeItem(
                    node = node,
                    onItemClick = {onIndividualClick(it)},
                    avatar = if(node.gender == 'M') painterResource(id = R.drawable.graphic_designer__1_)else painterResource(
                        id = R.drawable.woman__1_
                    ),
                    description = if(node.gender == 'M') "Male" else "Female"
                )

                 */
                //Text(text = "${node.firstName} ${node.lastName}")
                /*
                FavoriteItem(
                    node = node,
                    onItemClick = {onIndividualClick(it)},
                    avatar = if(node.gender == 'M') painterResource(id = R.drawable.icons8_male_64)else painterResource(
                        id = R.drawable.icons8_female_64
                    ),
                    description = if(node.gender == 'M') "Male" else "Female"
                )

                 */
                DogItem(
                    dog = node,
                    onClick = {onIndividualClick(it)} ,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )

            }
        }
    }
}

