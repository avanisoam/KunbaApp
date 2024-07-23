package com.example.kunbaapp.data.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kunbaapp.utils.EntityType
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Entity(tableName = "favorite")
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: EntityType,
    val refId: Int,
    val displayText : String,
    val addedOn: String = LocalDateTime.now().toString(),
): Parcelable
