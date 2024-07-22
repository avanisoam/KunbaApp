package com.example.kunbaapp.ui.family

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.ui.rootDetail.RootDetailDestination
import com.example.kunbaapp.ui.rootDetail.RootDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FamilyViewModel(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: IApiRepository
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

    init {
        getFamily()
    }
}

data class FamilyUiState(
    val family : FamilyDto = FamilyDto()
)