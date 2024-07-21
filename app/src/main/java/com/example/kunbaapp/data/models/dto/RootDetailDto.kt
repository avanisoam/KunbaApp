package com.example.kunbaapp.data.models.dto

data class RootDetailDto (
    val id: ULong,
    val families: List<FamilyDto>,    // List of image urls
    val nodes: List<NodeDto>
)