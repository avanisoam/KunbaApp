package com.example.kunbaapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.NodeDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface FamilyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFamily(familyDbo: FamilyDbo)

    @Query("SELECT * from family")
    fun getAllFamilies(): Flow<List<FamilyDbo>>

    @Query("SELECT * from family")
    fun getAllFamiliesV1(): List<FamilyDbo>

    @Query("SELECT * from family WHERE familyId = :familyId")
    fun getFamily(familyId: Int): FamilyDbo?

    @Query("SELECT * from family WHERE familyId = :familyId")
    fun getFamilyV1(familyId: Int): Flow<FamilyDbo?>

    /*
    @Query("SELECT children from family WHERE familyId = :familyId")
    fun checkChildrenExists(familyId: Int): List<ChildFamilyDto>

     */

    @Update
    suspend fun update(familyDbo: FamilyDbo)

}