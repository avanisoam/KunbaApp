package com.example.kunbaapp.data.network

import com.example.kunbaapp.data.models.dto.RootRegisterDto
import retrofit2.Response
import retrofit2.http.GET

interface KunbaAppApiService {
    @GET("roots")
    suspend fun fetchRoots()  : Response<List<RootRegisterDto>>
}