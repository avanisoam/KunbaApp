package com.example.kunbaapp.data.models.dto.NodeDtos

data class AddNodeDto(
    val nodeId : Int? = null,
    val rootId : Int? = 1,
    val familyId : Int? = null,
    val firstName : String = "",
    val lastName : String = "",
    val gender : Char = 'M',
    val dateOfBirth : String = "",
    val placeOfBirth : String = "",
    val image_Url : String = ""
)