package com.example.kunbaapp.ui.rootDetail

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.V2.RootDetailDtoV2
import com.example.kunbaapp.data.models.dto.timelineDtos.TempTimelineObject
import com.example.kunbaapp.data.models.dto.timelineDtos.TimelineObject
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.models.entity.RootDetailsDbo
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import com.example.kunbaapp.data.repository.OfflineApiRepository
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.data.repository.contract.IOfflineApiRepository
import com.example.kunbaapp.ui.family.toFamilyDbo
import com.example.kunbaapp.ui.family.toFamilyDto
import com.example.kunbaapp.ui.family.toNodeDbo
import com.example.kunbaapp.ui.family.toNodeDto
import com.example.kunbaapp.ui.home.HomeUiState
import com.example.kunbaapp.ui.home.toRootRegisterDbo
import com.example.kunbaapp.ui.node.NodeUiState
import com.example.kunbaapp.ui.poc.Poc_RootDetailUiState
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class RootDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository,
    private val offlineApiRepository: IOfflineApiRepository
): ViewModel(){


    val rootIdFromUrl: Int = checkNotNull(
        savedStateHandle[
            RootDetailDestination.ID_ARG
        ]
    )

    private val _uiState = MutableStateFlow<RootDetailUiState>(RootDetailUiState())
    val uiState: StateFlow<RootDetailUiState> = _uiState


    /*
    private fun getRootDetail(){
        viewModelScope.launch {
            Log.d("rootIdFromUrl", rootIdFromUrl.toString())
            val response = apiRepository.fetchRootDetails(rootIdFromUrl)
            Log.d("rootIdFromUrl", response.toString())
            val result = response.body()
            Log.d("URL", rootIdFromUrl.toString())
            if(result != null) {
                _uiState.update {
                    it.copy(
                        rootDetail = result
                    )
                }
                getTimelineObject()
            }
        }

    }

     */

    /*
    val uiState : Flow<RootDetailUiState> = apiRepository.fetchRootDetailHotFlow(rootIdFromUrl)
        .map {
           RootDetailUiState(
                rootDetail = it.body() ?: RootDetailDto(),
                rootTimeLineList = getTimelineObject(it.body() ?: RootDetailDto())
            )
        }
        .stateIn(
            scope = viewModelScope,
            //started = SharingStarted.Eagerly
            //started = SharingStarted.Lazily
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RootDetailUiState()
        )
     */

   private fun getRootDetailFromDb() : Flow<RootDetailsDbo>{

        var rootDetailsDbo : Flow<RootDetailsDbo> = flowOf()
        viewModelScope.launch(Dispatchers.IO) {
            val temp = offlineApiRepository.fetchRootDetailFlow(rootIdFromUrl)
            rootDetailsDbo = temp
        }
        return  rootDetailsDbo
    }

    val uiStateDb: Flow<RootDetailUiState> = offlineApiRepository.fetchRootDetailFlow(rootIdFromUrl)
        .map {
            RootDetailUiState(
                rootDetail = it.toRootDetailDto(),
                rootTimeLineList = getTimelineObject(
                    it.toRootDetailDto() ?: RootDetailDto()
                )
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RootDetailUiState()
        )

    /*
     val uiStateDb1: Deferred<StateFlow<RootDetailUiState>> =
         viewModelScope.async(Dispatchers.IO) {
                    offlineApiRepository.fetchRootDetailFlow(rootIdFromUrl)
                        //getRootDetailFromDb()
                        .map {
                            Log.d("DBFlow", getRootDetailFromDb().toString())
                            RootDetailUiState(
                                rootDetail = it.toRootDetailDto() ?: RootDetailDto(),
                                rootTimeLineList = getTimelineObject(
                                    it.toRootDetailDto() ?: RootDetailDto()
                                )
                            )
                        }
                        .stateIn(
                            scope = viewModelScope,
                            started = SharingStarted.WhileSubscribed(5000),
                            initialValue = RootDetailUiState()
                        )
                }


    val uiStateDb: Flow<RootDetailUiState> = flow { emitAll(uiStateDb1.await()) }
     */

    private fun getTimelineObject(rootDetail: RootDetailDto) : List<TimelineObject>{
        val tempTimeLine: MutableList<TimelineObject> = mutableListOf()
        if (rootDetail.familyDtos.isNotEmpty()) {
            val families = rootDetail.familyDtos//result.familyDtos

            val temp = families.forEach { family ->
                val fName = "${family.fatherInfo.firstName} ${family.fatherInfo.lastName}"
                val mName = "${family.motherInfo.firstName} ${family.motherInfo.lastName}"
                val t = TimelineObject(
                    initiator = TempTimelineObject(
                        id = family.familyId,
                        name = "$fName - $mName"
                    ),
                    children = rootDetail.nodeDtos
                        .filter { node ->
                            node.familyId == family.familyId
                        }
                        .map { child ->
                            val fullName = "${child.firstName} ${child.lastName}"
                            TempTimelineObject(
                                id = child.nodeId,
                                name = fullName
                            )
                        }

                )

                tempTimeLine.add(t)


            }

        }
        return tempTimeLine
    }
    private fun getRootDetailFlow(){
        apiRepository.fetchRootDetailHotFlow(rootIdFromUrl)
            .map {
                RootDetailUiState(
                    rootDetail = it.body() ?: RootDetailDto()
                )
            }
            .stateIn(
                scope = viewModelScope,
                //started = SharingStarted.Eagerly
                //started = SharingStarted.Lazily
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RootDetailUiState()
            )

    }

    /*
    fun toggleFavoriteButton(id: Int) {
        Log.d("Favorite - family", id.toString())
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteFromDb =
                databaseRepository.getFavoriteByTypeAndRefId(type = EntityType.Root, refId = id)
            //Log.d("Favorite - family", favoriteFromDb.toString())
            if (favoriteFromDb != null) {
                databaseRepository.removeFavorite(favoriteFromDb)
                Log.d("Favorite - family", "Removed from Favorites")
                _uiState.update {
                    it.copy(
                        isFavorite = false
                    )
                }
            }
            else {
                val favorite = Favorite(
                    id = 0,
                    type = EntityType.Root,
                    refId = id,
                    displayText = "Root: ${id}"
                )
                databaseRepository.addFavorite(favorite)
                Log.d("Favorite", "Added to Favorites")
                _uiState.update {
                    it.copy(
                        isFavorite = true
                    )
                }
            }
        }
    }

    private fun isFavoriteExist(){
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteFromDb = databaseRepository.getFavoriteByTypeAndRefId(
                type = EntityType.Root,
                refId = rootIdFromUrl
            )
            if(favoriteFromDb != null)
            {
                _uiState.update {
                    it.copy(
                        isFavorite = true
                    )
                }
            }
            else
            {
                _uiState.update {
                    it.copy(
                        isFavorite = false
                    )
                }
            }
        }
    }

   suspend fun getTimelineObject(){
        withContext(Dispatchers.IO) {
            if(_uiState.value.rootDetail.familyDtos.isNotEmpty())
            {
                val families = _uiState.value.rootDetail.familyDtos//result.familyDtos

                val tempTimeLine : MutableList<TimelineObject> = mutableListOf()

             val temp =  families.forEach {family ->
                     val fName = "${family.fatherInfo.firstName} ${family.fatherInfo.lastName}"
                     val mName = "${family.motherInfo.firstName} ${family.motherInfo.lastName}"
                     val t = TimelineObject(
                         initiator = TempTimelineObject(
                             id = family.familyId,
                             name = "$fName - $mName"
                         ),
                         children =  _uiState.value.rootDetail.nodeDtos
                             .filter {node ->
                                 node.familyId == family.familyId
                             }
                             .map {child ->
                             val fullName = "${child.firstName} ${child.lastName}"
                             TempTimelineObject(
                                 id = child.nodeId,
                                 name = fullName
                             )
                         }

                     )

                 tempTimeLine.add(t)



                 }

                _uiState.update {
                    it.copy(
                        rootTimeLineList = tempTimeLine
                    )
                }

            }
        }
    }

     */

    private fun checkAndSyncRootDetailData() {
        viewModelScope.launch(Dispatchers.IO) {
            val isLocal = offlineApiRepository.checkIsLocalState(rootIdFromUrl)
            if(isLocal.not())
            {
               val response = apiRepository.fetchRootDetails(rootIdFromUrl)
                val result = response.body()
                Log.d("RootDetail", result.toString())

                if(response.isSuccessful && result != null)
                {

                    result.nodeDtos.forEach {
                        offlineApiRepository.addNode(it.toNodeDbo())
                    }


                    result.familyDtos.forEach{familyDto ->
                        val family = FamilyDbo(
                            familyId = familyDto.familyId,
                            fatherId = familyDto.fatherInfo.nodeId?: null,
                            motherId = familyDto.motherInfo.nodeId?:null,
                            fatherInfo = familyDto.fatherInfo.toNodeDbo(),
                            motherInfo = familyDto.motherInfo.toNodeDbo(),
                            children = listOf()
                        )
                        offlineApiRepository.addFamily(family)
                    }


                    /*

                    result.familyDtos.forEach {
                        offlineApiRepository.addFamily(it.toFamilyDbo())
                    }

                     */


                }

            }
        }
    }

    private fun getRootDetailV2(){
        viewModelScope.launch {
            Log.d("rootIdFromUrl", rootIdFromUrl.toString())
            val response = apiRepository.fetchRootDetailsV2(rootIdFromUrl)
            Log.d("rootIdFromUrl", response.toString())
            val result = response.body()
            Log.d("URL", rootIdFromUrl.toString())
            if(result != null) {
                _uiState.update {
                    it.copy(
                        rootDetailV2 = result,
                        /*
                        rootTimeLineList = getTimelineObject(
                            result
                        )

                         */
                    )
                }
                //getTimelineObject()
            }
        }

    }

    init {
        getRootDetailV2()
        checkAndSyncRootDetailData()
            //getRootDetail()
            //getFavoritesFromDb()
            //getRootDetailFlow()
            //isFavoriteExist()

    }

}

data class RootDetailUiState(
    val rootDetail : RootDetailDto = RootDetailDto(),
    val rootDetailV2 : RootDetailDtoV2 = RootDetailDtoV2(),
    //val favoritesRootIds: List<Int> = listOf(),
    val isFavorite: Boolean = false,
    val rootTimeLineList : List<TimelineObject> = listOf(),
    val rootDetailDbo : RootDetailsDbo = RootDetailsDbo(0, listOf(), listOf())
)

fun RootDetailsDbo.toRootDetailDto() : RootDetailDto = RootDetailDto(
    rootId = rootId,
    familyDtos = familyDbos.map { it.toFamilyDto() },
    nodeDtos = nodeDbos.map { it.toNodeDto() }

)