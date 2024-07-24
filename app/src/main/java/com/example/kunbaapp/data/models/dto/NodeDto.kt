package com.example.kunbaapp.data.models.dto

data class NodeDto(
    val nodeId : Int = 0,
    val rootId : Int = 0,
    val familyId : Int = 0,
    val firstName : String = "",
    val lastName : String = "",
    val gender : Char = 'x',
    //val isFavorite : Boolean = false
)
