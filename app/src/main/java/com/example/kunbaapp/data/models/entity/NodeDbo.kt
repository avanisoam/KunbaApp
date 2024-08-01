package com.example.kunbaapp.data.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "node")
@Parcelize
data class NodeDbo(
    @PrimaryKey
    val nodeId : Int = 0,
    val rootId : Int = 0,
    val familyId : Int? = null,
    val firstName : String = "",
    val lastName : String = "",
    val gender : Char = 'M',
    val dateOfBirth : String = "",
    val placeOfBirth : String = "",
    val image_Url : String = ""
) : Parcelable
