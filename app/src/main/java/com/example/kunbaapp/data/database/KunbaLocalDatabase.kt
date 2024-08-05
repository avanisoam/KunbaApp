package com.example.kunbaapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kunbaapp.data.models.entity.ChildrenFamilyTypeConvertor
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.models.entity.NodeTypeConvertor
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import com.example.kunbaapp.utils.EntityTypeConverter

@Database(entities = [Favorite::class,RootRegisterDbo::class,NodeDbo::class,FamilyDbo::class], version = 9, exportSchema = false)
@TypeConverters(EntityTypeConverter::class,NodeTypeConvertor::class, ChildrenFamilyTypeConvertor::class)
abstract class KunbaLocalDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun rootRegisterDao(): RootRegisterDao
    abstract fun nodeDao(): NodeDao
    abstract fun familyDao(): FamilyDao
}