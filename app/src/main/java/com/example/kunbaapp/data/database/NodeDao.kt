package com.example.kunbaapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kunbaapp.data.models.entity.NodeDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNode(nodeDbo: NodeDbo)

    @Query("SELECT * from node")
    fun getAllNodes(): Flow<List<NodeDbo>>
}