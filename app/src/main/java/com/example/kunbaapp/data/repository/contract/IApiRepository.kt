package com.example.kunbaapp.data.repository.contract

import com.example.kunbaapp.data.models.dto.RootRegisterDto
import retrofit2.Response

interface IApiRepository {
    suspend fun fetchRoots(): Response<List<RootRegisterDto>>
}