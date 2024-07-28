package com.example.kunbaapp.ui.poc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.repository.contract.IApiRepository
import kotlinx.coroutines.launch

class Poc_RootDetailViewModel(
    private val apiRepository: IApiRepository,
): ViewModel() {

    //val currentRootDetailAsLiveData : LiveData<Poc_RootDetailUiState> = MutableLiveData()

    fun getRootDetail(){
        // TODO
    }

    init {
        //getRootDetail()
        viewModelScope.launch {
            //getDogBreedData()
            apiRepository
                .fetchRootDetailsV1(3).collect{breedList ->
                val result = breedList.body()
                if(breedList.isSuccessful && result != null) {
                    Log.d("RAW_FLOW", "Received Item : ${breedList.toString()}")
                   // _uiState.value = HomeUiState.Success(dogBreedsList = result.message)
                }
            }
        }
    }

}

data class Poc_RootDetailUiState(
    val rootDetail : RootDetailDto = RootDetailDto(),
    val selectedRootId: Int = 3
)