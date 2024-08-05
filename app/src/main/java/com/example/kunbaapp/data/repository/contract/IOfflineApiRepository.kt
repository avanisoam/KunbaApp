package com.example.kunbaapp.data.repository.contract

import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.models.entity.RootDetailsDbo
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IOfflineApiRepository {

    suspend fun addRootRegister(rootRegisterDbo: RootRegisterDbo)

    fun getRootRegisters(): Flow<List<RootRegisterDbo>>
    fun getRootRegistersV1(): List<RootRegisterDbo>

    fun getRoot(rootId: Int): RootRegisterDbo?

    fun checkIsLocalState(rootId: Int) : Boolean

    suspend fun addNode(nodeDbo: NodeDbo)

    fun getNodes(): Flow<List<NodeDbo>>

    fun getNode(nodeId: Int): NodeDbo?

    fun getNodeV1(nodeId: Int): Flow<NodeDbo?>

    suspend fun addFamily(familyDbo: FamilyDbo)

    fun getFamilies(): Flow<List<FamilyDbo>>

    fun getFamily(familyId: Int): FamilyDbo?
    fun getFamilyV1(familyId: Int): Flow<FamilyDbo?>

    //fun checkChildrenExists(familyId: Int): List<ChildFamilyDto>

    suspend fun update(familyDbo: FamilyDbo)

    fun fetchRootDetailFlow(rootId: Int) : Flow<RootDetailsDbo>
}