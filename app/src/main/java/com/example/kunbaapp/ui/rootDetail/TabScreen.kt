package com.example.kunbaapp.ui.rootDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.timelineDtos.TimelineObject

@Composable
fun TabScreen(
    families: List<FamilyDto>,
    onItemClick: (Int) -> Unit,
    onIndividualClick: (Int) -> Unit,
    nodes: List<NodeDto>,
    timelineObjects: List<TimelineObject>,
    modifier: Modifier = Modifier
) {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Family", "People", "Timeline")

    Column(modifier = modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.Face, contentDescription = null)
                            1 -> Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
                            2 -> Icon(imageVector = Icons.Default.Info, contentDescription = null)
                        }
                    }
                )
            }
        }
        when (tabIndex) {
            0 -> FamilyScreen(
                families = families,
                onItemClick = onItemClick,
                onIndividualClick = onIndividualClick,
            )
            1 -> PeopleScreen(
                nodes = nodes,
                onIndividualClick = onIndividualClick,
            )
            2 -> TimelineScreen(
                timelineObjects = timelineObjects,
                navigateToFamilyScreen = onItemClick,
                navigateToNodeScreen = onIndividualClick
                )
        }
    }
}