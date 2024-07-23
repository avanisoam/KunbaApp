package com.example.kunbaapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.utils.EntityTypeConverter

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
@TypeConverters(EntityTypeConverter::class)
abstract class KunbaLocalDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

}