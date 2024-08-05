package com.example.kunbaapp.data.models.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChildFamilyDto(
    val nodeId: Int = 0,
    val marriageId: Int = 0
): Parcelable
