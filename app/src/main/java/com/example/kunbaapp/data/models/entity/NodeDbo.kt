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
    val firstName : String? = null,
    val lastName : String? = null,
    val gender : Char? = null,
    val dateOfBirth : String? = null,
    val placeOfBirth : String? = null,
    val image_Url : String? = null
) : Parcelable
