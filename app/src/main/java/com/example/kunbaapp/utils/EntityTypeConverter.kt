package com.example.kunbaapp.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class EntityTypeConverter {
    @TypeConverter
    fun toEntityFromType(value: Int): EntityType = enumValues<EntityType>()[value]
    @TypeConverter
    fun fromEntityToType(value: EntityType): Int = value.ordinal
}