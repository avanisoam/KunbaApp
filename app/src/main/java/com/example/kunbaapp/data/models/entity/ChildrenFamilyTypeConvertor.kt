package com.example.kunbaapp.data.models.entity

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class ChildrenFamilyTypeConvertor {
    @TypeConverter
    fun toChildrenFamilyList(data: String): List<ChildFamilyDto> {
        val listType = object : TypeToken<ArrayList<ChildFamilyDto>>() {}.type
        return GsonBuilder().create().fromJson(data, listType)
    }

    @TypeConverter
    fun toChildrenFamilyString(breed: List<ChildFamilyDto>): String {
        return GsonBuilder().create().toJson(breed)
    }
}