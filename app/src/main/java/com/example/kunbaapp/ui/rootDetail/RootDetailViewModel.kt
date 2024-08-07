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
import com.example.kunbaapp.ui.family.toFamilyDtoV2
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

    val uiStateDb: Flow<RootDetailUiState> = offlineApiRepository.fetchRootDetailFlow(rootIdFromUrl)
        .map {
            RootDetailUiState(
                rootDetailV2 = it.toRootDetailDtoV2(),
                rootTimeLineList = getTimelineObject(
                    it.toRootDetailDtoV2() ?: RootDetailDtoV2()
                )
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RootDetailUiState()
        )

    private fun getTimelineObject(rootDetail: RootDetailDtoV2) : List<TimelineObject>{
        Log.d("TEST",rootDetail.toString() )
        val tempTimeLine: MutableList<TimelineObject> = mutableListOf()
        if (rootDetail.familyDtos.isNullOrEmpty().not()) {
            val families = rootDetail.familyDtos//result.familyDtos
            Log.d("TEST",families.toString() )
            val temp = families?.forEach { family ->

                val fName = "${family.fatherInfo?.firstName} ${family.fatherInfo?.lastName}"
                val mName = "${family.motherInfo?.firstName} ${family.motherInfo?.lastName}"

                val t = rootDetail.nodeDtos
                    ?.filter { node ->
                        node.familyId == family.familyId
                    }?.let {

                        TimelineObject(
                            initiator = TempTimelineObject(
                                id = family.familyId,
                                name = "$fName - $mName"
                            ),
                            children = it
                                .map { child ->
                                    val fullName = "${child.firstName} ${child.lastName}"
                                    TempTimelineObject(
                                        id = child.nodeId,
                                        name = fullName
                                    )
                                }

                        )

                    }

                t?.let { tempTimeLine.add(it) }


            }

        }
        return tempTimeLine
    }

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

    private fun checkAndSyncRootDetailData() {
        viewModelScope.launch(Dispatchers.IO) {
            val isLocal = offlineApiRepository.checkIsLocalState(rootIdFromUrl)
            if(isLocal.not())
            {
               //val response = apiRepository.fetchRootDetails(rootIdFromUrl)
               val response = apiRepository.fetchRootDetailsV2(rootIdFromUrl)
                val result = response.body()
                Log.d("RootDetail", result.toString())

                if(response.isSuccessful && result != null)
                {

                    result.nodeDtos?.forEach {
                        offlineApiRepository.addNode(it.toNodeDbo())
                    }


                    result.familyDtos?.forEach{familyDto ->
                        val family = FamilyDbo(
                            familyId = familyDto.familyId,
                            fatherId = familyDto.fatherInfo?.nodeId,
                            motherId = familyDto.motherInfo?.nodeId,
                            fatherInfo = familyDto.fatherInfo?.toNodeDbo(),
                            motherInfo = familyDto.motherInfo?.toNodeDbo(),
                            children = listOf()
                        )
                        offlineApiRepository.addFamily(family)
                        withContext(Dispatchers.IO){
                            familyDto.motherInfo?.toNodeDbo()
                                ?.let { offlineApiRepository.addNode(it) }
                        }
                    }
                }

            }
        }
    }

    init {
        checkAndSyncRootDetailData()
        isFavoriteExist()

    }

}

data class RootDetailUiState(
    val rootDetailV2 : RootDetailDtoV2 = RootDetailDtoV2(),
    val isFavorite: Boolean = false,
    val rootTimeLineList : List<TimelineObject> = listOf(),
)

fun RootDetailsDbo.toRootDetailDto() : RootDetailDto = RootDetailDto(
    rootId = rootId,
    familyDtos = familyDbos.map { it.toFamilyDto() },
    nodeDtos = nodeDbos.map { it.toNodeDto() }

)

fun RootDetailsDbo.toRootDetailDtoV2(): RootDetailDtoV2 = RootDetailDtoV2(
    rootId = rootId,
    familyDtos = familyDbos.map { it.toFamilyDtoV2() },
    nodeDtos = nodeDbos.map { it.toNodeDto() }
)