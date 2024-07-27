package com.example.kunbaapp.data.models.dto.timelineDtos

data class NodeTimelineDto(
    val nodeId : Int = 0,
    val fatherNodeId: Int = 0,
    val gender: Char = 'M',
    val firstName: String = "",
    val lastName: String = "",
    //val dateOfBirth: String = "",
    val familyId: Int = 0,
    val index: Int = 0
)
