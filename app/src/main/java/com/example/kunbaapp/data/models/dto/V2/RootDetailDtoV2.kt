package com.example.kunbaapp.data.models.dto.V2

import com.example.kunbaapp.data.models.dto.NodeDto

data class RootDetailDtoV2(
    val rootId: Int = 0,
    val familyDtos: List<FamilyDtoV2>? = null,
    val nodeDtos: List<NodeDto>? = null
)
