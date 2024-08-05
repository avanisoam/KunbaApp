package com.example.kunbaapp.data.models.dto.V2

data class RootRegisterDtoV2(
    val id: Int = 0,
    val rootName: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val familyName: String? = null,
    val rootNodeId: Int? = null
    // TODO: ParentRootId: Int? = null
)
