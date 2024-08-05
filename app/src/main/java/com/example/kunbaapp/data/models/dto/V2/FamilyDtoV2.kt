package com.example.kunbaapp.data.models.dto.V2

import com.example.kunbaapp.data.models.dto.NodeDto

data class FamilyDtoV2(
    val familyId: Int = 0,
    val fatherInfo: NodeDto? = null,
    val motherInfo: NodeDto? = null
)
