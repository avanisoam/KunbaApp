package com.example.kunbaapp.ui.favorite

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.entity.Favorite
import com.example.kunbaapp.data.repository.contract.IDatabaseRepository
import com.example.kunbaapp.utils.EntityType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(
    savedStateHandle: SavedStateHandle,
    private val databaseRepository: IDatabaseRepository
) : ViewModel() {

    private val entityFromUrl = savedStateHandle.get<String>("entity")

    val uiState: StateFlow<FavoriteUiState> =
        databaseRepository.getFavorites().map { it ->
            FavoriteUiState(
                favorites = it.reversed(),
                selectedEntity = entityFromUrl?:""
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoriteUiState()
        )

    fun loadData() {
        viewModelScope.launch {

            val root = Favorite(
                id = 0,
                type = EntityType.Root,
                refId = 1,
                displayText = "First Root"
            )
            databaseRepository.addFavorite(root)

            val family = Favorite(
                id = 0,
                type = EntityType.Family,
                refId = 1,
                displayText = "First Family"
            )
            databaseRepository.addFavorite(family)

            val node = Favorite(
                id = 0,
                type = EntityType.Node,
                refId = 1,
                displayText = "First Node"
            )
            databaseRepository.addFavorite(node)
        }
    }

    init {
        //loadData()
    }
}

data class FavoriteUiState (
    val favorites: List<Favorite>? = null,
    val availableEntityTypes: List<String> = listOf("Root","Family","Node"),
    val showFilter: Boolean = false,
    val selectedEntity: String = ""
)