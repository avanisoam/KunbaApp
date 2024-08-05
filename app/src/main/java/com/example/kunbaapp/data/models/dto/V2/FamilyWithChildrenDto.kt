package com.example.kunbaapp.data.models.dto.V2

import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto

data class FamilyWithChildrenDto(
    val familyId: Int = 0,
    val fatherNode: NodeDto? = null,
    val motherNode: NodeDto? = null,
    val children: List<NodeDto>? = null,
    val childrenFamily: List<ChildFamilyDto>? = null
)
