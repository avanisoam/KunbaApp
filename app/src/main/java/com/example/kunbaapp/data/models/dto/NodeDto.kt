package com.example.kunbaapp.data.models.dto

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class NodeDto(
    val nodeId : Int = 0,
    val rootId : Int = 0,
    val familyId : Int? = null,
    val firstName : String = "",
    val lastName : String = "",
    val gender : Char = 'M',
    var dateOfBirth : String = "",
    val placeOfBirth : String = "",
    val image_Url : String = ""
)
{
fun formatToDate() : String {
    val text = LocalDateTime.parse(dateOfBirth).toString()//dateOfBirth//"2022-01-06 20:30:45"
    //val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    //val localDateTime = LocalDateTime.parse(text, pattern)

    return text
    //Assertions.assertThat(localDateTime).isEqualTo("2022-01-06T20:30:45")
}
}
