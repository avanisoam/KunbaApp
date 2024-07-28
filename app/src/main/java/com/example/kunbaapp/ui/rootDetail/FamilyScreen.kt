package com.example.kunbaapp.ui.rootDetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto

@Composable
fun FamilyScreen(
    families: List<FamilyDto>,
    onItemClick: (Int) -> Unit,
    onIndividualClick: (Int) -> Unit,
) {
    //Text(text = "FamilyScreen")
    RootDetailBody(
        families = families,
        onItemClick = onItemClick,
        onIndividualClick = onIndividualClick,
    )
}