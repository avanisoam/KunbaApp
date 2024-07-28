package com.example.kunbaapp.ui.rootDetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kunbaapp.data.models.dto.timelineDtos.TimelineObject
import com.example.kunbaapp.ui.shared.Timeline.LazyTimelineKunba

@Composable
fun TimelineScreen(
    timelineObjects: List<TimelineObject>,
    navigateToFamilyScreen : (Int) -> Unit,
    navigateToNodeScreen: (Int) -> Unit
) {
    //Text(text = timelineObjects.toString())
    LazyTimelineKunba(
        stages= timelineObjects,
        navigateToFamilyScreen = navigateToFamilyScreen,
        navigateToNodeScreen = navigateToNodeScreen
    )
}