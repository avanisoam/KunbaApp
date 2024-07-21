package com.example.kunbaapp.data.network

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
}