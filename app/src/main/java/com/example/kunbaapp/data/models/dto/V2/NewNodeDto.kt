package com.example.kunbaapp.data.models.dto.V2

import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeTimelineDto

data class NewNodeDto(
    val individual: NodeDto = NodeDto(),
    val ancestors : List<NodeTimelineDto>? = null
)
