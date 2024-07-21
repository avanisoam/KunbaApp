package com.example.kunbaapp.ui.rootDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.repository.contract.IApiRepository
import com.example.kunbaapp.ui.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RootDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: IApiRepository
): ViewModel(){


    val rootIdFromUrl: Int = checkNotNull(
        savedStateHandle[
            RootDetailDestination.ID_ARG
        ]
    )

    private val _uiState = MutableStateFlow<RootDetailUiState>(RootDetailUiState())
    val uiState: StateFlow<RootDetailUiState> = _uiState


    private fun getRootDetail(){
        viewModelScope.launch {
            Log.d("rootIdFromUrl", rootIdFromUrl.toString())
            val response = apiRepository.fetchRootDetails(rootIdFromUrl)
            Log.d("rootIdFromUrl", response.toString())
            val result = response.body()
            Log.d("URL", rootIdFromUrl.toString())
            if(result != null) {
                _uiState.update {
                    it.copy(
                        rootDetail = result
                    )
                }
            }
        }

    }
    init {
        getRootDetail()
    }

}

data class RootDetailUiState(
    val rootDetail : RootDetailDto = RootDetailDto()
)