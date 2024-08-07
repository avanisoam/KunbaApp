package com.example.kunbaapp.ui.shared.Timeline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.data.models.dto.timelineDtos.TimelineObject
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeStatus
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeTimelineDto
import com.example.kunbaapp.data.models.dto.timelineDtos.TempTimelineObject
import java.time.LocalDate

@Composable
internal fun MessageKunba(
    //hiringStage: TimelineObject,
    hiringStage: TempTimelineObject,
    onClick: (Int) -> Unit,
    cardAlignment: Alignment = Alignment.CenterStart,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.65f)
                //.align(Alignment.CenterStart)
                //.align(Alignment.CenterEnd)
                .align(cardAlignment)
                .clickable { onClick(hiringStage.id) },
            colors = CardDefaults.cardColors(
                //containerColor = getBackgroundColor(hiringStage)
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            //val fullName = "${hiringStage.initiator.firstName} ${hiringStage.initiator.lastName}"
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(12.dp),
                text = hiringStage.name,//fullName,
                textAlign =  TextAlign.Start,//getTextAlign(hiringStage),
                //style = getTextStyle(hiringStage),
                //fontWeight = getFontWeight(hiringStage),
                //color = getTextColor(hiringStage)
            )
        }
    }
}

/*
@Composable
private fun getBoxAlign(hiringStage: TimelineObject) =
    if (hiringStage.initiator is NodeTimelineDto) {
        Alignment.CenterEnd
    } else {
        Alignment.CenterStart
    }

@Composable
private fun getTextAlign(hiringStage: TimelineObject) =
    if (hiringStage.initiator is NodeTimelineDto) {
        TextAlign.End
    } else {
        TextAlign.Start
    }
 */

@Composable
private fun getBackgroundColor(hiringStage: TimelineObject) = when (hiringStage.status) {
    NodeStatus.UPCOMING -> MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
    NodeStatus.CURRENT -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
    else -> MaterialTheme.colorScheme.surfaceVariant
}

@Composable
private fun getTextColor(hiringStage: TimelineObject) =
    if (hiringStage.status == NodeStatus.UPCOMING) {
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.63f)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

@Composable
private fun getFontWeight(hiringStage: TimelineObject) =
    if (hiringStage.status == NodeStatus.CURRENT) {
        W500
    } else {
        W400
    }

@Composable
private fun getTextStyle(hiringStage: TimelineObject) =
    if (hiringStage.status == NodeStatus.CURRENT) {
        MaterialTheme.typography.bodyLarge
    } else {
        MaterialTheme.typography.bodyMedium
    }

/*
@Preview(showBackground = true)
@Composable
private fun MessagePreview() {
    //KunbaAppTheme {
        MessageKunba(
            hiringStage = TimelineObject(
                date = LocalDate.now(),
                initiator = NodeTimelineDto(
                    nodeId = 1,
                    fatherNodeId = 1,
                    gender = 'F',
                    firstName = "Avani",
                    lastName = "Soam",
                    dateOfBirth = "2nd January 1990",
                    familyId = 1,
                    index = 0
                ),
                status = NodeStatus.CURRENT
            ),
            modifier = Modifier,
            onClick = {}
        )
   // }
}

@Preview(showBackground = true)
@Composable
private fun UpcomingStageMessagePreview() {
    //KunbaAppTheme {
        MessageKunba(
            hiringStage = TimelineObject(
                date = LocalDate.now(),
                initiator = NodeTimelineDto(
                    nodeId = 1,
                    fatherNodeId = 1,
                    gender = 'F',
                    firstName = "Avani",
                    lastName = "Soam",
                    dateOfBirth = "2nd January 1990",
                    familyId = 1,
                    index = 0
                ),
                status = NodeStatus.UPCOMING
            ),
            modifier = Modifier,
            onClick = {}
        )
  //  }
}

@Preview(showBackground = true)
@Composable
private fun FinishedMessagePreview() {
    //KunbaAppTheme {
        MessageKunba(
            modifier = Modifier,
            hiringStage = TimelineObject(
                date = LocalDate.now(),
                initiator = NodeTimelineDto(
                    nodeId = 1,
                    fatherNodeId = 1,
                    gender = 'F',
                    firstName = "Avani",
                    lastName = "Soam",
                    dateOfBirth = "2nd January 1990",
                    familyId = 1,
                    index = 0
                ),
                status = NodeStatus.FINISHED,

            ),
            onClick = {}
        )
    }

 */
//}
