package com.example.kunbaapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private fun getRoots() {
        viewModelScope.launch(Dispatchers.IO){
            val response = apiRepository.fetchRoots()
            val result = response.body()
            if(response.isSuccessful || result != null)
            {
                _uiState.update {
                    it.copy(
                        roots = result!!
                    )
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
        getRoots()
        getFavoritesFromDb()
    }
}

data class HomeUiState(
    val roots: List<RootRegisterDto> = listOf(),
    val favoritesRootIds: List<Int> = listOf(),
    //val isFavorite: Boolean = false
)