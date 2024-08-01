package com.example.kunbaapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.NodeDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface FamilyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFamily(familyDbo: FamilyDbo)

    @Query("SELECT * from family")
    fun getAllFamilies(): Flow<List<FamilyDbo>>

    @Query("SELECT * from family WHERE familyId = :familyId")
    fun getFamily(familyId: Int): FamilyDbo?
}