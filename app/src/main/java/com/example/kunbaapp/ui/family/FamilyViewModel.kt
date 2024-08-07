package com.example.kunbaapp.ui.family

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.dto.V2.FamilyDtoV2
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
import kotlinx.coroutines.withContext

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

    val uiStateFamilyDb : Flow<FamilyUiState> = offlineApiRepository.getFamilyV2(familyIdFromUrl)
        .map {
            FamilyUiState(
                familyV2 = it?.toFamilyWithChildrenDto()?: FamilyWithChildrenDto()

            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FamilyUiState()
        )

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

   private fun checkAndSyncChildrenFamilyInDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val familyFromDb = offlineApiRepository.getFamily(familyIdFromUrl)
           // val childrenInFamily = offlineApiRepository.checkChildrenExists(familyIdFromUrl)
            if(familyFromDb != null) {
                if (familyFromDb.childrenFamily == listOf<ChildFamilyDto>()) {
                    //val response = apiRepository.getChildrenFamily(familyIdFromUrl)
                    val response = apiRepository.fetchFamilyV2(familyIdFromUrl)
                    val result = response.body()

                    if (response.isSuccessful && result != null) {

                        offlineApiRepository.addFamily(result.toFamilyDbo())
                    }
                }
            }
            else{
                val familyFromApi = apiRepository.fetchFamilyV2(familyIdFromUrl)
                val result = familyFromApi.body()
                if(familyFromApi.isSuccessful && result != null) {
                    Log.d("Result", result.toString())
                    val fatherNode = result.fatherNode
                    withContext(Dispatchers.IO) {
                        if (fatherNode != null) {
                            offlineApiRepository.addNode(fatherNode.toNodeDbo())
                        }
                    }
                    val motherNode = result.motherNode
                    if(motherNode != null) {
                        offlineApiRepository.addNode(motherNode.toNodeDbo())
                    }
                    val children = result.children
                    Log.d("Result", fatherNode.toString())
                    Log.d("Result", motherNode.toString())
                    Log.d("Result", children.toString())
                    offlineApiRepository.addFamily(result.toFamilyDbo())
                    result.children?.forEach {
                        Log.d("Result", it.toString())
                        offlineApiRepository.addNode(it.toNodeDbo())
                    }
                }
            }
        }
    }

    init {
        checkAndSyncChildrenFamilyInDb()
        isFavoriteExist()
    }
}

data class FamilyUiState(
    val familyV2 : FamilyWithChildrenDto = FamilyWithChildrenDto(),
    val isFavorite: Boolean = false,
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

fun FamilyDbo.toFamilyDtoV2() : FamilyDtoV2 = FamilyDtoV2(
    familyId = familyId,
    fatherInfo = fatherInfo?.toNodeDto(),
    motherInfo = motherInfo?.toNodeDto()
)

fun FamilyDbo.toFamilyWithChildrenDto(): FamilyWithChildrenDto = FamilyWithChildrenDto(
    familyId = familyId,
    fatherNode = fatherInfo?.toNodeDto(),
    motherNode = motherInfo?.toNodeDto(),
    children = children.map { it.toNodeDto() },
    childrenFamily = childrenFamily
)

fun FamilyWithChildrenDto.toFamilyDbo(): FamilyDbo = FamilyDbo(
    familyId = familyId,
    fatherId = fatherNode?.nodeId,
    motherId = motherNode?.nodeId,
    fatherInfo = fatherNode?.toNodeDbo(),
    motherInfo = motherNode?.toNodeDbo(),
    //children = children?.map { it.toNodeDbo() } ?: listOf(),
    children = listOf(),
    childrenFamily= childrenFamily ?: listOf()
)