package com.example.kunbaapp.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "root_register")
data class RootRegisterDbo(
    @PrimaryKey
    val rootId: Int,
    val rootName: String,
    val isLocal: Boolean = false,
    val ownerId: Int? = null,
    val isApproved: Boolean = true,
    val lastModified: String? = null
)
