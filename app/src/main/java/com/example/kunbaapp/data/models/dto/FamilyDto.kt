package com.example.kunbaapp.data.models.dto

data class FamilyDto(
    val familyId : Int = 0,

    val fatherInfo : NodeDto = NodeDto(),

    val motherInfo : NodeDto = NodeDto(),

    val children : List<NodeDto> = listOf(),
)
