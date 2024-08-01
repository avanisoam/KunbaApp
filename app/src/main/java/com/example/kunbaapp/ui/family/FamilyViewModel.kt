package com.example.kunbaapp.ui.family

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.ChildFamilyDto
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        //getFamily()
        getFamilyFromDb()
        getChildrenFamily()
        //getFavoritesFromDb()
        isFavoriteExist()
    }
}

data class FamilyUiState(
    val family : FamilyDto = FamilyDto(),
    //val favoritesFromDb: List<Favorite> = listOf(),
    val isFavorite: Boolean = false,
    val childrenFamily : List<ChildFamilyDto> = listOf()
)

fun FamilyDbo.toFamilyDto() : FamilyDto = FamilyDto(
    familyId = familyId,
    fatherInfo = fatherInfo.toNodeDto(),
    motherInfo = motherInfo.toNodeDto(),
    children = children.map {
        it.toNodeDto()
    }
)

fun NodeDbo.toNodeDto() : NodeDto = NodeDto(
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