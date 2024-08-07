package com.example.kunbaapp.data.network

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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KunbaAppApiService {
    @GET("MobileApi/roots")
    suspend fun fetchRoots()  : Response<List<RootRegisterDto>>

    @GET("MobileApi/{rootId}/roots")
    suspend fun fetchRootDetails(
        @Path("rootId") rootId: Int
    ): Response<RootDetailDto>

    @GET("MobileApi/{familyId}/family")
    suspend fun fetchFamily(
        @Path("familyId") familyId: Int
    ): Response<FamilyDto>

    @GET("MobileApi/{nodeId}/node")
    suspend fun fetchNode(
        @Path("nodeId") nodeId: Int
    ) : Response<NodeDto>

    @GET("MobileApi/{familyId}/ChildFamily")
    suspend fun getChildrenFamily(
        @Path("familyId") familyId: Int
    ): Response<List<ChildFamilyDto>>

    @POST("MobileApi/addNode")
    suspend fun addNode(@Body addNodeDto: AddNodeDto) : Response<NodeDto>

    @POST("MobileApi/getFamilyTimeLine")
    suspend fun getFamilyTimeLine(@Body nodeDto: NodeDto) : Response<List<NodeTimelineDto>>

    @POST("MobileApi/addPartner/{nodeId}")
    suspend fun addPartner(
        @Path("nodeId") nodeId: Int
    ) : Response<NodeDto>

    @POST("MobileApi/addNewParents/{nodeId}")
    suspend fun addParents(
        @Path("nodeId") nodeId: Int
    ) : Response<NodeDto>

    @POST("MobileApi/addNewSibling/{nodeId}")
    suspend fun addSibling(
        @Path("nodeId") nodeId: Int
    ) : Response<NodeDto>

    @POST("MobileApi/addChildWithNewPartner/{fatherId}")
    suspend fun addChild(
        @Path("fatherId") fatherId: Int
    ) : Response<NodeDto>

    @PUT("MobileApi/{nodeId}/updateNode")
    suspend fun updateNode(
        @Path("nodeId") nodeId: Int,
        @Body nodeDto: NodeDto
    ) : Response<NodeDto>

    @GET("MobileApiV2/roots")
    suspend fun fetchRootsV2()  : Response<List<RootRegisterDtoV2>>

    @GET("MobileApiV2/{rootId}/roots")
    suspend fun fetchRootDetailsV2(
        @Path("rootId") rootId: Int
    ): Response<RootDetailDtoV2>

    @GET("MobileApiV2/{familyId}/family")
    suspend fun fetchFamilyV2(
        @Path("familyId") familyId: Int
    ): Response<FamilyWithChildrenDto>

    @GET("MobileApiV2/{nodeId}/getNode")
    suspend fun fetchNodeV2(
        @Path("nodeId") nodeId: Int
    ) : Response<NewNodeDto>
}