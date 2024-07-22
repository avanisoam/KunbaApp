package com.example.kunbaapp.data.network

import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface KunbaAppApiService {
    @GET("roots")
    suspend fun fetchRoots()  : Response<List<RootRegisterDto>>

    @GET("{rootId}/roots")
    suspend fun fetchRootDetails(
        @Path("rootId") rootId: Int
    ): Response<RootDetailDto>

    @GET("{familyId}/family")
    suspend fun fetchFamily(
        @Path("familyId") familyId: Int
    ): Response<FamilyDto>

    @GET("{nodeId}/node")
    suspend fun fetchNode(
        @Path("nodeId") nodeId: Int
    ) : Response<NodeDto>
}