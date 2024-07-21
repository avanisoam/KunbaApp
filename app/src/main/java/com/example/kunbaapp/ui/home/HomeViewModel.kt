package com.example.kunbaapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.data.repository.contract.IApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val apiRepository: IApiRepository
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

    init {
        getRoots()
    }
}

data class HomeUiState(
    val roots: List<RootRegisterDto> = listOf()
)