package com.example.kunbaapp.data.repository

import android.util.Log
import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.NodeDtos.AddNodeDto
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeTimelineDto
import com.example.kunbaapp.data.network.KunbaAppApiService
import com.example.kunbaapp.data.repository.contract.IApiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class ApiRepository(private val kunbaAppApiService: KunbaAppApiService): IApiRepository {
    override suspend fun fetchRoots(): Response<List<RootRegisterDto>> = kunbaAppApiService.fetchRoots()
    override suspend fun fetchRootDetails(rootId: Int): Response<RootDetailDto> = kunbaAppApiService.fetchRootDetails(rootId)
    override suspend fun fetchRootDetailsV1(rootId: Int): Flow<Response<RootDetailDto>> = flow {
        while(true){
            Log.d("RAW_FLOW", "Fetching Root ${rootId} details.")
            val rootDetailDto = kunbaAppApiService.fetchRootDetails(rootId)
            emit(rootDetailDto)
            delay(5000)
        }
    }

    override fun fetchRootDetailHotFlow(rootId: Int): Flow<Response<RootDetailDto>> = flow {
        while(true){
            Log.d("RAW_FLOW", "Fetching Root ${rootId} details.")
            val rootDetailDto = kunbaAppApiService.fetchRootDetails(rootId)
            emit(rootDetailDto)
            delay(5000)
        }
    }

    override suspend fun fetchFamily(familyId: Int): Response<FamilyDto> = kunbaAppApiService.fetchFamily(familyId)
    override suspend fun fetchNode(nodeId: Int): Response<NodeDto> = kunbaAppApiService.fetchNode(nodeId)
    override suspend fun getChildrenFamily(familyId: Int): Response<List<ChildFamilyDto>> = kunbaAppApiService.getChildrenFamily(familyId)
    override suspend fun addNode(addNodeDto: AddNodeDto): Response<NodeDto> = kunbaAppApiService.addNode(addNodeDto)
    override suspend fun getFamilyTimeLine(nodeDto: NodeDto): Response<List<NodeTimelineDto>> = kunbaAppApiService.getFamilyTimeLine(nodeDto)
    override suspend fun addPartner(nodeId: Int): Response<NodeDto> = kunbaAppApiService.addPartner(nodeId)
    override suspend fun addParents(nodeId: Int): Response<NodeDto> = kunbaAppApiService.addParents(nodeId)

    override suspend fun addSibling(nodeId: Int): Response<NodeDto> = kunbaAppApiService.addSibling(nodeId)
    override suspend fun addChild(fatherNodeId: Int): Response<NodeDto> =
        kunbaAppApiService.addChild(fatherNodeId)

    override suspend fun updateNode(nodeId: Int, nodeDto: NodeDto): Response<NodeDto> =
        kunbaAppApiService.updateNode(nodeId, nodeDto)
}