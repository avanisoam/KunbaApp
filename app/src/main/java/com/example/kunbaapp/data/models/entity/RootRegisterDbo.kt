package com.example.kunbaapp.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "root_register")
data class RootRegisterDbo(
    @PrimaryKey
    val rootId: Int,
    val rootName: String
)
