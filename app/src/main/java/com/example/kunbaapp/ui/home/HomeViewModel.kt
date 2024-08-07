package com.example.kunbaapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.models.dto.V2.RootRegisterDtoV2
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.models.entity.RootRegisterDbo
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.data.repository.contract.IOfflineApiRepository
import com.example.kunbaapp.ui.family.FamilyUiState
import com.example.kunbaapp.ui.node.NodeUiState
import com.example.kunbaapp.ui.rootDetail.RootDetailUiState
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository,
    private val offlineApiRepository: IOfflineApiRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    val uiStateDb : Flow<HomeUiState> = offlineApiRepository.getRootRegisters()
        .map {
            HomeUiState(
                rootsV2 = it.map {rootDbo ->
                    rootDbo.toRootRegisterDtoV2()
                },
                //favoritesRootIds = getFavoritesFromDb()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

       private fun checkAndSyncRootRegisterData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = offlineApiRepository.getRootRegistersV1()
            if(response == listOf<RootRegisterDbo>())
            {
                // Call Api and fill the table root_register
                val rootsFromApi = apiRepository.fetchRootsV2()
                val result = rootsFromApi.body()
                if(rootsFromApi.isSuccessful && result != null)
                {
                    result.forEach {
                        offlineApiRepository.addRootRegister(it.toRootRegisterDbo())
                    }
                }
            }
        }
    }

    private fun getFavoritesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = databaseRepository.getFavoriteByType(EntityType.Root)

            _uiState.update {
                it.copy(
                    favoritesRootIds = response.map{
                        it.refId
                    }.toList()
                )
            }
        }
    }

    fun toggleFavoriteButton(id: Int) {
        Log.d("Favorite - root", id.toString())
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteFromDb =
                databaseRepository.getFavoriteByTypeAndRefId(type = EntityType.Root, refId = id)
            //Log.d("Favorite - root", favoriteFromDb.toString())
            if (favoriteFromDb != null) {
                databaseRepository.removeFavorite(favoriteFromDb)
                Log.d("Favorite - root", "Removed from Favorites")

            }
            else {
                val favorite = Favorite(
                    id = 0,
                    type = EntityType.Root,
                    refId = id,
                    displayText = "Root: ${id}"
                )
                databaseRepository.addFavorite(favorite)
                Log.d("Favorite - root", "Added to Favorites")
            }
        }
    }

    init {
        checkAndSyncRootRegisterData()
        getFavoritesFromDb()
    }
}

data class HomeUiState(
    //val roots: List<RootRegisterDto> = listOf(),
    val favoritesRootIds: List<Int> = listOf(),
    val rootsV2: List<RootRegisterDtoV2> = listOf()
    //val isFavorite: Boolean = false
)

fun RootRegisterDbo.toRootRegisterDto() : RootRegisterDto = RootRegisterDto(
    rootId = rootId,
    rootName = rootName
)

fun RootRegisterDto.toRootRegisterDbo() : RootRegisterDbo = RootRegisterDbo(
    rootId = rootId,
    rootName = rootName
)

fun RootRegisterDtoV2.toRootRegisterDbo(): RootRegisterDbo = RootRegisterDbo(
    rootId = id,
    rootName= rootName?:""
)

fun RootRegisterDbo.toRootRegisterDtoV2(): RootRegisterDtoV2 = RootRegisterDtoV2(
    id = rootId,
    rootName = rootName,
    latitude = null,
    longitude = null,
    familyName = null,
    rootNodeId = null
)

