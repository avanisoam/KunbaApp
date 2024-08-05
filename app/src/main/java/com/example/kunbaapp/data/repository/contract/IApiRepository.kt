package com.example.kunbaapp.data.repository.contract

import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.NodeDtos.AddNodeDto
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.models.dto.V2.FamilyWithChildrenDto
import com.example.kunbaapp.data.models.dto.V2.NewNodeDto
import com.example.kunbaapp.data.models.dto.V2.RootDetailDtoV2
import com.example.kunbaapp.data.models.dto.V2.RootRegisterDtoV2
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeTimelineDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IApiRepository {
    suspend fun fetchRoots(): Response<List<RootRegisterDto>>

    suspend fun fetchRootDetails(rootId: Int) : Response<RootDetailDto>

    suspend fun fetchRootDetailsV1(rootId: Int) : Flow<Response<RootDetailDto>>

    fun fetchRootDetailHotFlow(rootId: Int) : Flow<Response<RootDetailDto>>

    suspend fun fetchFamily(familyId: Int) : Response<FamilyDto>

    suspend fun fetchNode(nodeId: Int) : Response<NodeDto>

    fun fetchNodeHotFlow(nodeId: Int) : Flow<Response<NodeDto>>

    suspend fun getChildrenFamily(familyId: Int) : Response<List<ChildFamilyDto>>

    suspend fun addNode(addNodeDto: AddNodeDto) : Response<NodeDto>

    suspend fun getFamilyTimeLine(nodeDto: NodeDto) : Response<List<NodeTimelineDto>>

    suspend fun addPartner(nodeId: Int) : Response<NodeDto>

    suspend fun addParents(nodeId: Int) : Response<NodeDto>

    suspend fun addSibling(nodeId: Int) : Response<NodeDto>
    suspend fun addChild(fatherNodeId: Int) : Response<NodeDto>

    suspend fun updateNode(nodeId: Int, nodeDto: NodeDto) : Response<NodeDto>

    suspend fun fetchRootsV2(): Response<List<RootRegisterDtoV2>>

    suspend fun fetchRootDetailsV2(rootId: Int) : Response<RootDetailDtoV2>

    suspend fun fetchFamilyV2(familyId: Int) : Response<FamilyWithChildrenDto>

    suspend fun fetchNodeV2(nodeId: Int) : Response<NewNodeDto>
}