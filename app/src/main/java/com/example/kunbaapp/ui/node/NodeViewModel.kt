package com.example.kunbaapp.ui.node

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.ui.family.FamilyDestination
import com.example.kunbaapp.ui.family.FamilyUiState
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NodeViewModel(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: IApiRepository,
    private val databaseRepository: IDatabaseRepository
) : ViewModel() {
    val nodeIdFromUrl: Int = checkNotNull(
        savedStateHandle[
            NodeDestination.ID_ARG
        ]
    )
    /*
    val uniqueIdFromUrl: String = checkNotNull(
        savedStateHandle[
            NodeDestination.UniqueId_ARG
        ]
    )
     */

    private val _uiState = MutableStateFlow<NodeUiState>(NodeUiState())
    val uiState: StateFlow<NodeUiState> = _uiState

    private fun getNode() {
        viewModelScope.launch {
            val response = apiRepository.fetchNode(nodeIdFromUrl)
            val result = response.body()
            Log.d("URL", nodeIdFromUrl.toString())
            if (result != null) {
                _uiState.update {
                    it.copy(
                        node = result
                    )
                }
            }
        }
    }
    fun toggleFavoriteButton(id: Int) {
        Log.d("Favorite", id.toString())
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteFromDb =
                databaseRepository.getFavoriteByTypeAndRefId(type = EntityType.Node, refId = id)
            Log.d("Favorite", favoriteFromDb.toString())
            if (favoriteFromDb != null) {
                databaseRepository.removeFavorite(favoriteFromDb)
                Log.d("Favorite", "Removed from Favorites")
                _uiState.update {
                    it.copy(
                        isFavorite = false
                    )
                }
            }
            else {
                val favorite = Favorite(
                    id = 0,
                    type = EntityType.Node,
                    refId = id,
                    displayText = "Node: ${id}"
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
                type = EntityType.Node,
                refId = nodeIdFromUrl
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

    fun setUniqueId(value: String){
        _uiState.update {
            it.copy(
                uniqueId = value
            )
        }
    }

    init {
        getNode()
        //getFavoritesFromDb()
        isFavoriteExist()
    }
}


data class NodeUiState(
    val node: NodeDto = NodeDto(),
    //val favoritesFromDb: List<Favorite> = listOf(),
    val isFavorite: Boolean = false,
    val uniqueId: String = ""
)