package com.example.kunbaapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.NodeDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNode(nodeDbo: NodeDbo)

    @Query("SELECT * from node")
    fun getAllNodes(): Flow<List<NodeDbo>>

    @Query("SELECT * from node WHERE nodeId = :nodeId")
    fun getNode(nodeId: Int): NodeDbo?

    @Query("SELECT * from node WHERE nodeId = :nodeId")
    fun getNodeV1(nodeId: Int): Flow<NodeDbo?>

    @Query("SELECT * from node WHERE rootId = :rootId")
    fun getNodesByRootId(rootId: Int): List<NodeDbo>

    @Query("SELECT * from node WHERE rootId = :rootId")
    fun getNodesByRootIdV1(rootId: Int): Flow<List<NodeDbo>>

    @Query("SELECT * from node WHERE familyId = :familyId")
    fun getNodesByFamilyId(familyId: Int): List<NodeDbo>?

}