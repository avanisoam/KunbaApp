package com.example.kunbaapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Query("SELECT * from favorite")
    fun getAllFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from favorite WHERE id = :id")
    fun getFavoriteById(id: Int): Favorite

    @Query("SELECT * from favorite WHERE type= :type")
    fun getFavoriteByType(type: EntityType): List<Favorite>

    @Query("SELECT * from favorite WHERE type= :type AND refId= :refId")
    fun getFavoriteByRefIdAndType(type: EntityType, refId:Int): Favorite?

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}