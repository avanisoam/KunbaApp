package com.example.kunbaapp.ui.rootDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.timelineDtos.TempTimelineObject
import com.example.kunbaapp.data.models.dto.timelineDtos.TimelineObject
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.ui.home.HomeUiState
import com.example.kunbaapp.ui.poc.Poc_RootDetailUiState
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class RootDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository
): ViewModel(){


    val rootIdFromUrl: Int = checkNotNull(
        savedStateHandle[
            RootDetailDestination.ID_ARG
        ]
    )

    private val _uiState = MutableStateFlow<RootDetailUiState>(RootDetailUiState())
    val uiState: StateFlow<RootDetailUiState> = _uiState


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

    private fun getRootDetailFlow(){
        apiRepository.fetchRootDetailHotFlow(3)
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
                initialValue = Poc_RootDetailUiState()
            )

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

    init {
            getRootDetail()
            //getFavoritesFromDb()
            isFavoriteExist()

    }

}

data class RootDetailUiState(
    val rootDetail : RootDetailDto = RootDetailDto(),
    //val favoritesRootIds: List<Int> = listOf(),
    val isFavorite: Boolean = false,
    val rootTimeLineList : List<TimelineObject> = listOf()
)