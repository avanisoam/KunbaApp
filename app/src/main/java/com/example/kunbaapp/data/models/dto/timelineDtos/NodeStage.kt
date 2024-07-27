package com.example.kunbaapp.data.models.dto.timelineDtos

import java.time.LocalDate

data class NodeStage(
    val date: LocalDate?,
    val initiator: TimeLineDto,
    val status: NodeStatus
)
