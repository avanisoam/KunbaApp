package com.example.kunbaapp.data.models.dto.timelineDtos

import java.time.LocalDate

data class TimelineObject(
    val date: LocalDate? = LocalDate.now(),

    // TODO: Revisit object for this
    //val initiator: NodeTimelineDto = NodeTimelineDto(),
    val initiator: TempTimelineObject = TempTimelineObject(),
    val children: List<TempTimelineObject> = listOf(),

    val status: NodeStatus = NodeStatus.FINISHED
)
