package com.example.kunbaapp.data.models.dto

data class NodeDto(
    val nodeId : ULong,
    val rootId : ULong,
    val familyId : ULong,
    val firstName : String,
    val lastName : String,
    val gender : Char,
)
