package com.example.kunbaapp.data.repository

import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.network.KunbaAppApiService
import com.example.kunbaapp.data.repository.contract.IApiRepository
import retrofit2.Response

class ApiRepository(private val kunbaAppApiService: KunbaAppApiService): IApiRepository {
    override suspend fun fetchRoots(): Response<List<RootRegisterDto>> = kunbaAppApiService.fetchRoots()
}