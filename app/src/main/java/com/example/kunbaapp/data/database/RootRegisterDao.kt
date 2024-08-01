package com.example.kunbaapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface RootRegisterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRootRegister(rootRegisterDbo: RootRegisterDbo)

    @Query("SELECT * from root_register")
    fun getAllroots(): Flow<List<RootRegisterDbo>>
}