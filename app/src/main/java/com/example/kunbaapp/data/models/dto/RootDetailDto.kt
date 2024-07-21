package com.example.kunbaapp.data.models.dto

data class RootDetailDto (
    val rootId: Int = 0,
    val familyDtos: List<FamilyDto> = listOf(),
    val nodeDtos: List<NodeDto> = listOf()
)