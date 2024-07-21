package com.example.kunbaapp.data.models.dto

data class RootDetailDto (
    val id: ULong = 0u,
    val families: List<FamilyDto> = listOf(),    // List of image urls
    val nodes: List<NodeDto> = listOf()
)