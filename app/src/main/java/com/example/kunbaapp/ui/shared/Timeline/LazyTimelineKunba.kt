package com.example.kunbaapp.ui.shared.Timeline

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.timelineDtos.TimelineObject
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeStatus
import com.example.kunbaapp.modules.timeline.TimelineChildNode
import com.example.kunbaapp.modules.timeline.TimelineNode
import com.example.kunbaapp.modules.timeline.defaults.CircleParametersDefaults
import com.example.kunbaapp.modules.timeline.defaults.LineParametersDefaults
import com.example.kunbaapp.modules.timeline.models.LineParameters
import com.example.kunbaapp.modules.timeline.models.StrokeParameters
import com.example.kunbaapp.modules.timeline.models.TimelineNodePosition

@Composable
fun LazyTimelineKunba(
    stages: List<TimelineObject>,//Array<NodeStage>,
    navigateToFamilyScreen : (Int) -> Unit,
    navigateToNodeScreen: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        content = {
            itemsIndexed(stages) { index, hiringStage ->
                TimelineNode(
                    position = mapToTimelineNodePosition(index, stages.size),
                    circleParameters = CircleParametersDefaults.circleParameters(
                        backgroundColor = getIconColor(hiringStage),
                        stroke = getIconStrokeColor(hiringStage),
                        icon = getIcon(hiringStage)
                    ),
                    lineParameters = getLineBrush(
                        circleRadius = 12.dp,
                        index = index,
                        items = stages
                    ),
                    contentStartOffset = 16.dp,
                    spacer = 24.dp
                ) { modifier ->
                    MessageKunba(
                        hiringStage = hiringStage.initiator,
                        modifier = modifier,
                        onClick = { navigateToFamilyScreen(it) }
                    )
                }

                if (hiringStage.children.isNotEmpty()) {
                    hiringStage.children.forEach {child ->
                        TimelineChildNode(
                            position = mapToTimelineNodePosition(index, stages.size),
                            circleParameters = CircleParametersDefaults.circleParameters(
                                backgroundColor = getIconColor(hiringStage),
                                stroke = getIconStrokeColor(hiringStage),
                                icon = getIcon(hiringStage)
                            ),
                            lineParameters = getLineBrush(
                                circleRadius = 12.dp,
                                index = index,
                                items = stages
                            ),
                            contentStartOffset = 16.dp,
                            spacer = 24.dp
                        ) { modifier ->
                            MessageKunba(
                                hiringStage = child,
                                modifier = modifier,
                                cardAlignment = Alignment.CenterEnd,
                                onClick = { navigateToNodeScreen(it) }
                            )
                            //BoxExample()
                        }
                    }
                }
            }
        },
        contentPadding = PaddingValues(16.dp)
    )
}

@Composable
private fun getLineBrush(
    circleRadius: Dp,
    index: Int,
    items: List<TimelineObject>
): LineParameters? {
    return if (index != items.lastIndex) {
        val currentStage: TimelineObject = items[index]
        val nextStage: TimelineObject = items[index + 1]
        val circleRadiusInPx = with(LocalDensity.current) { circleRadius.toPx() }
        LineParametersDefaults.linearGradient(
            strokeWidth = 3.dp,
            startColor = (getIconStrokeColor(currentStage)?.color ?: getIconColor(currentStage)),
            endColor = (getIconStrokeColor(nextStage)?.color ?: getIconColor(items[index + 1])),
            startY = circleRadiusInPx * 2
        )
    } else {
        null
    }
}

private fun getIconColor(stage: TimelineObject): Color {
    return when (stage.status) {
        NodeStatus.FINISHED -> Color(0xFF00FF86)
        NodeStatus.CURRENT -> Color(0xFFFF8700)
        NodeStatus.UPCOMING -> Color.White
    }
}

private fun getIconStrokeColor(stage: TimelineObject): StrokeParameters? {
    return if (stage.status == NodeStatus.UPCOMING) {
        StrokeParameters(color = Color(0xFFEEEBF4), width = 2.dp)
    } else {
        null
    }
}

@Composable
private fun getIcon(stage: TimelineObject): Int? {
    return if (stage.status == NodeStatus.CURRENT) {
        R.drawable.ic_bubble_warning_16
    } else {
        null
    }
}

private fun mapToTimelineNodePosition(index: Int, collectionSize: Int) = when (index) {
    0 -> TimelineNodePosition.FIRST
    collectionSize - 1 -> TimelineNodePosition.LAST
    else -> TimelineNodePosition.MIDDLE
}