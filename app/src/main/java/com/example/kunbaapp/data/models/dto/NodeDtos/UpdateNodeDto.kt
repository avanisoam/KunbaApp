package com.example.kunbaapp.data.models.dto.NodeDtos

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class UpdateNodeDto(
    val nodeId : Int = 0,
    val rootId : Int = 0,
    val familyId : String = "",
    val firstName : String = "",
    val lastName : String = "",
    val gender : String = "",
    val dateOfBirth : String = "",
    val placeOfBirth : String = "",
    val image_Url : String = ""
)