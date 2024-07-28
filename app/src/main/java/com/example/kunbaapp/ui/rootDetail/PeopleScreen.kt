package com.example.kunbaapp.ui.rootDetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto

@Composable
fun PeopleScreen(
    onIndividualClick: (Int) -> Unit,
    nodes: List<NodeDto>,
) {
    //Text(text = "People Screen")
    NodesBody(
        nodes = nodes,
        onIndividualClick = onIndividualClick,
    )
}