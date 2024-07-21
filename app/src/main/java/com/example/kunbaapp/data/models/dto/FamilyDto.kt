package com.example.kunbaapp.data.models.dto

data class FamilyDto(
    val familyId : Int,

    val fatherInfo : NodeDto,

    val motherInfo : NodeDto,

    val children : List<NodeDto>,
)
