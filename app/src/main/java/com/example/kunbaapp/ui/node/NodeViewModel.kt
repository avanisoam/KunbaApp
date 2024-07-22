package com.example.kunbaapp.ui.node

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.ui.family.FamilyDestination
import com.example.kunbaapp.ui.family.FamilyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NodeViewModel(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: IApiRepository
): ViewModel() {
    val nodeIdFromUrl: Int = checkNotNull(
        savedStateHandle[
            NodeDestination.ID_ARG
        ]
    )

    private val _uiState = MutableStateFlow<NodeUiState>(NodeUiState())
    val uiState: StateFlow<NodeUiState> = _uiState

    private fun getNode(){
        viewModelScope.launch {
            val response = apiRepository.fetchNode(nodeIdFromUrl)
            val result = response.body()
            Log.d("URL", nodeIdFromUrl.toString())
            if(result != null) {
                _uiState.update {
                    it.copy(
                        node = result
                    )
                }
            }
        }
    }

    init {
        getNode()
    }
}

data class NodeUiState(
    val node : NodeDto = NodeDto()
)