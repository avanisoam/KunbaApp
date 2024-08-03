package com.example.kunbaapp.data.models.entity


data class RootDetailsDbo(
    val rootId: Int = 0,
    val familyDbos: List<FamilyDbo>,
    val nodeDbos: List<NodeDbo>
)
