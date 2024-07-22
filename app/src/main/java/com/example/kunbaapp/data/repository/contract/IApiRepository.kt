package com.example.kunbaapp.data.repository.contract

import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import retrofit2.Response

interface IApiRepository {
    suspend fun fetchRoots(): Response<List<RootRegisterDto>>

    suspend fun fetchRootDetails(rootId: Int) : Response<RootDetailDto>

    suspend fun fetchFamily(familyId: Int) : Response<FamilyDto>
}