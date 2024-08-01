package com.example.kunbaapp.data.repository.contract

import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import kotlinx.coroutines.flow.Flow

interface IOfflineApiRepository {

    suspend fun addRootRegister(rootRegisterDbo: RootRegisterDbo)

    fun getRootRegisters(): Flow<List<RootRegisterDbo>>

    suspend fun addNode(nodeDbo: NodeDbo)

    fun getNodes(): Flow<List<NodeDbo>>

    suspend fun addFamily(familyDbo: FamilyDbo)

    fun getFamilies(): Flow<List<FamilyDbo>>

    fun getFamily(familyId: Int): FamilyDbo?
}