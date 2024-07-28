package com.example.kunbaapp.data.repository.contract

import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.NodeDtos.AddNodeDto
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.models.dto.timelineDtos.NodeTimelineDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IApiRepository {
    suspend fun fetchRoots(): Response<List<RootRegisterDto>>

    suspend fun fetchRootDetails(rootId: Int) : Response<RootDetailDto>

    suspend fun fetchRootDetailsV1(rootId: Int) : Flow<Response<RootDetailDto>>

    suspend fun fetchFamily(familyId: Int) : Response<FamilyDto>

    suspend fun fetchNode(nodeId: Int) : Response<NodeDto>

    suspend fun getChildrenFamily(familyId: Int) : Response<List<ChildFamilyDto>>

    suspend fun addNode(addNodeDto: AddNodeDto) : Response<NodeDto>

    suspend fun getFamilyTimeLine(nodeDto: NodeDto) : Response<List<NodeTimelineDto>>
}