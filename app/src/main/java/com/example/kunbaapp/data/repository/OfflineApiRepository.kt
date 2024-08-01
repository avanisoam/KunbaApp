package com.example.kunbaapp.data.repository

import com.example.kunbaapp.data.database.FamilyDao
import com.example.kunbaapp.data.database.NodeDao
import com.example.kunbaapp.data.database.RootRegisterDao
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import com.example.kunbaapp.data.repository.contract.IOfflineApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OfflineApiRepository(
    private val rootRegisterDao: RootRegisterDao,
    private val nodeDao: NodeDao,
    private val familyDao: FamilyDao
    ) : IOfflineApiRepository {

    override suspend fun addRootRegister(rootRegisterDbo: RootRegisterDbo) = rootRegisterDao.insertRootRegister(rootRegisterDbo)

    override fun getRootRegisters(): Flow<List<RootRegisterDbo>> = rootRegisterDao.getAllroots()
    override suspend fun addNode(nodeDbo: NodeDbo) = nodeDao.insertNode(nodeDbo)

    override fun getNodes(): Flow<List<NodeDbo>> = nodeDao.getAllNodes()
    override suspend fun addFamily(familyDbo: FamilyDbo) = familyDao.insertFamily(familyDbo)

    override fun getFamilies(): Flow<List<FamilyDbo>> = familyDao.getAllFamilies()
    override fun getFamily(familyId: Int): FamilyDbo? =
        familyDao.getFamily(familyId)

}