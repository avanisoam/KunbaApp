package com.example.kunbaapp.data.repository

import android.util.Log
import com.example.kunbaapp.data.database.FamilyDao
import com.example.kunbaapp.data.database.NodeDao
import com.example.kunbaapp.data.database.RootRegisterDao
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.models.entity.RootDetailsDbo
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import com.example.kunbaapp.data.repository.contract.IOfflineApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.withContext

class OfflineApiRepository(
    private val rootRegisterDao: RootRegisterDao,
    private val nodeDao: NodeDao,
    private val familyDao: FamilyDao
    ) : IOfflineApiRepository {

    override suspend fun addRootRegister(rootRegisterDbo: RootRegisterDbo) = rootRegisterDao.insertRootRegister(rootRegisterDbo)

    override fun getRootRegisters(): Flow<List<RootRegisterDbo>> = rootRegisterDao.getAllroots()
    override fun getRoot(rootId: Int): RootRegisterDbo? = rootRegisterDao.getRootRegister(rootId)

    override suspend fun addNode(nodeDbo: NodeDbo) = nodeDao.insertNode(nodeDbo)

    override fun getNodes(): Flow<List<NodeDbo>> = nodeDao.getAllNodes()
    override fun getNode(nodeId: Int): NodeDbo? = nodeDao.getNode(nodeId)
    override fun getNodeV1(nodeId: Int): Flow<NodeDbo?> = nodeDao.getNodeV1(nodeId)

    override suspend fun addFamily(familyDbo: FamilyDbo) = familyDao.insertFamily(familyDbo)

    override fun getFamilies(): Flow<List<FamilyDbo>> = familyDao.getAllFamilies()
    override fun getFamily(familyId: Int): FamilyDbo? =
        familyDao.getFamily(familyId)

    override fun getFamilyV1(familyId: Int): Flow<FamilyDbo?> = familyDao.getFamilyV1(familyId)
        /*
        flow{
        familyDao.getFamily(familyId)
    }

         */

    override fun fetchRootDetailFlow(rootId: Int): Flow<RootDetailsDbo> = flow {
        while(true){
            Log.d("RAW_FLOW", "Fetching Root ${rootId} details.")
            var rootDetailDbo = RootDetailsDbo(rootId, listOf(), listOf())

            /*
            // If the following Dao method is not exposed as flow then android will throw an error that Db operations cannot be run on main thread
            // By exposing it as flow and room will handle the thread shifting from Main to any other thread
            val nodes = nodeDao.getNodesByRootIdV1(rootId)

            val families1 = familyDao.getAllFamilies()

            merge(nodes, families1).collect()
            { }

            nodes.combine(families1){node, fam ->
                rootDetailDbo =  RootDetailsDbo(
                    rootId = 1,
                    familyDbos = fam,
                    nodeDbos = node
                )
                //rootDetailDbo
            }.collect()

             */

            withContext(Dispatchers.IO) {
                val nodes = nodeDao.getNodesByRootId(rootId)

                //rootDetailDbo.nodeDbos = nodes

                val fatherIds = nodes.map {
                    it.nodeId
                }

                val families = familyDao.getAllFamiliesV1()
                    .filter {
                    //it.fatherInfo.nodeId != null && fatherIds.contains(it.fatherInfo.nodeId)
                    it.fatherId!= null && fatherIds.contains(it.fatherId)
                }

                //rootDetailDbo.familyDbos = families

                rootDetailDbo = RootDetailsDbo(rootId, families, nodes)

                /*
                // java.lang.IllegalStateException: Flow invariant is violated:
                // Flow was collected in [StandaloneCoroutine{Active}@24b79eb, Dispatchers.Main.immediate],
                // but emission happened in [DispatchedCoroutine{Active}@ab348, Dispatchers.IO].
                // Please refer to 'flow' documentation or use 'flowOn' instead
                //withContext(Dispatchers.IO)
                withContext(Dispatchers.Main)
                {
                    emit(rootDetailDbo)
                    delay(5000)
                }
                 */

            }

            emit(rootDetailDbo)
            delay(5000)
        }
    }
    /*
    {
        /*
        val nodes = nodeDao.getNodesByRootId(rootId)

        val fatherIds = nodes.map {
            it.nodeId
        }

        val families = familyDao.getAllFamiliesV1().filter {
            it.fatherInfo.nodeId != null && fatherIds.contains(it.fatherInfo.nodeId)
        }

        val rootDetailDbo = flow<RootDetailsDbo>{
            RootDetailsDbo(
            rootId = rootId,
            familyDbos = families,
            nodeDbos = nodes
        )}
        */
        val rootDetailDbo =  flow<RootDetailsDbo> {
            RootDetailsDbo(
                rootId = 1,
                familyDbos = listOf(),
                nodeDbos = listOf(
                    NodeDbo(
                        nodeId = 1,
                        rootId = 1,
                        familyId = 1,
                        firstName = "Me1234",
                        lastName = "Singh1234",
                        gender = 'M',
                        dateOfBirth = "2017-03-02 10:10:10",
                        placeOfBirth = "Gurgaon123",
                        image_Url = "Image Url"
                    )
                )
            )
        }
        return  rootDetailDbo

    }

     */

}

private fun <T> Flow<T>.collect() {
    //TODO("Not yet implemented")
}
