package com.example.kunbaapp.ui.family

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.V2.FamilyWithChildrenDto
import com.example.kunbaapp.data.models.entity.FamilyDbo
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.models.entity.NodeDbo
import com.example.kunbaapp.data.repository.OfflineApiRepository
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.data.repository.contract.IOfflineApiRepository
import com.example.kunbaapp.ui.rootDetail.RootDetailDestination
import com.example.kunbaapp.ui.rootDetail.RootDetailUiState
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FamilyViewModel(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository,
    private val offlineApiRepository: IOfflineApiRepository
): ViewModel(){

    val familyIdFromUrl: Int = checkNotNull(
        savedStateHandle[
            FamilyDestination.ID_ARG
        ]
    )

    private val _uiState = MutableStateFlow<FamilyUiState>(FamilyUiState())
    val uiState: StateFlow<FamilyUiState> = _uiState

    val uiStateFamilyDb : Flow<FamilyUiState> = offlineApiRepository.getFamilyV1(familyIdFromUrl)
        .map {
            FamilyUiState(
                family = it?.toFamilyDto()?: FamilyDto()

            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FamilyUiState()
        )

    private fun getFamily(){
        viewModelScope.launch {
            val response = apiRepository.fetchFamily(familyIdFromUrl)
            val result = response.body()
            if(result != null)
            {
                _uiState.update {
                    it.copy(
                        family = result
                    )
                }
            }
        }
    }

    private fun getFamilyFromDb(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = offlineApiRepository.getFamily(familyIdFromUrl)

            if(response != null)
            {
                _uiState.update {
                    it.copy(
                        family = response.toFamilyDto()
                    )
                }
            }
        }
    }

    /*
    private fun getFavoritesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = databaseRepository.getFavoriteByType(EntityType.Family)
            _uiState.update {
                it.copy(
                    favoritesFromDb = response
                )
            }
        }
    }
     */

    fun toggleFavoriteButton(id: Int) {
        Log.d("Favorite - family", id.toString())
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteFromDb =
                databaseRepository.getFavoriteByTypeAndRefId(type = EntityType.Family, refId = id)
            Log.d("Favorite - family", favoriteFromDb.toString())
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
                    type = EntityType.Family,
                    refId = id,
                    displayText = "Family: ${id}"
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
                type = EntityType.Family,
                refId = familyIdFromUrl
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

    fun getChildrenFamily() {
        viewModelScope.launch {
            val response = apiRepository.getChildrenFamily(familyIdFromUrl)
            val result = response.body()
            if(result != null)
            {
                _uiState.update {
                    it.copy(
                        childrenFamily = result
                    )
                }
            }
        }
    }

    fun checkAndSyncChildrenFamilyInDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val familyFromDb = offlineApiRepository.getFamily(familyIdFromUrl)
           // val childrenInFamily = offlineApiRepository.checkChildrenExists(familyIdFromUrl)

            if(familyFromDb?.childrenFamily == listOf<ChildFamilyDto>()) {
                val response = apiRepository.getChildrenFamily(familyIdFromUrl)
                val result = response.body()

                if(response.isSuccessful && result != null) {
                    familyFromDb.childrenFamily = result
                    offlineApiRepository.update(familyFromDb)
                    /*
                    _uiState.update {
                        it.copy(
                            childrenFamily = result
                        )
                    }

                     */
                }
            }
        }
    }

    private fun getFamilyV2(){
        viewModelScope.launch {
            val response = apiRepository.fetchFamilyV2(familyIdFromUrl)
            val result = response.body()
            if(result != null)
            {
                _uiState.update {
                    it.copy(
                        familyV2 = result
                    )
                }
            }
        }
    }

    init {
        getFamilyV2()
        //checkAndSyncChildrenFamilyInDb()
        //getFamily()
        //getFamilyFromDb()
        //getChildrenFamily()
        //getFavoritesFromDb()
        isFavoriteExist()
    }
}

data class FamilyUiState(
    val family : FamilyDto = FamilyDto(),
    val familyV2 : FamilyWithChildrenDto = FamilyWithChildrenDto(),
    //val favoritesFromDb: List<Favorite> = listOf(),
    val isFavorite: Boolean = false,
    val childrenFamily : List<ChildFamilyDto> = listOf(),
    val familyDbo: FamilyDbo = FamilyDbo(0,0,0, NodeDbo(), NodeDbo(), listOf()),
    val listOfFamilies: List<FamilyDbo> = listOf()
)

fun FamilyDbo.toFamilyDto() : FamilyDto = FamilyDto(
    familyId = familyId,
    fatherInfo = fatherInfo?.toNodeDto() ?: NodeDto(),
    motherInfo = motherInfo?.toNodeDto() ?: NodeDto(),
    children = children.map {
        it.toNodeDto() ?: NodeDto()
    }
)

fun FamilyDto.toFamilyDbo() : FamilyDbo = FamilyDbo(
    familyId = familyId,
    fatherId = fatherInfo.nodeId?:null,
    motherId = motherInfo.nodeId?: null,
    fatherInfo = fatherInfo.toNodeDbo() ?: null,
    motherInfo = motherInfo.toNodeDbo() ?: null,
    children = children.map {
        it.toNodeDbo() ?: NodeDbo()
    }
)

fun NodeDbo.toNodeDto() : NodeDto = NodeDto(
    nodeId = nodeId,
    familyId = familyId,
    rootId = rootId,
    firstName = firstName?: "",
    lastName = lastName?: "",
    gender = gender?: 'M',
    dateOfBirth = dateOfBirth?: "",
    placeOfBirth = placeOfBirth?: "",
    image_Url = image_Url?: ""
)

fun NodeDto.toNodeDbo() : NodeDbo = NodeDbo(
    nodeId = nodeId,
    familyId = familyId,
    rootId = rootId,
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    dateOfBirth = dateOfBirth,
    placeOfBirth = placeOfBirth,
    image_Url = image_Url
)