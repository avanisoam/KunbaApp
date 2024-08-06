package com.example.kunbaapp.data.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import kotlinx.parcelize.Parcelize

@Entity(tableName = "family")
@Parcelize
data class FamilyDbo(
    @PrimaryKey
    val familyId : Int,

    val fatherId: Int?,

    val motherId: Int?,

    val fatherInfo : NodeDbo?,

    val motherInfo : NodeDbo?,

    var children : List<NodeDbo> = listOf(),

    var childrenFamily: List<ChildFamilyDto> = listOf()
) : Parcelable
