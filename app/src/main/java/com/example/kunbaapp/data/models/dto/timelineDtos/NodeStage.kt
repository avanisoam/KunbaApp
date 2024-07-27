package com.example.kunbaapp.data.models.dto.timelineDtos

import java.time.LocalDate

data class NodeStage(
    val date: LocalDate? = LocalDate.now(),
    val initiator: NodeTimelineDto = NodeTimelineDto(),
    val status: NodeStatus = NodeStatus.FINISHED
)
