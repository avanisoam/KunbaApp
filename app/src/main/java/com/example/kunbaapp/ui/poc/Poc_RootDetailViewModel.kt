package com.example.kunbaapp.ui.poc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.kunbaapp.data.models.dto.RootDetailDto
import com.example.kunbaapp.data.repository.contract.IApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class Poc_RootDetailViewModel(
    private val apiRepository: IApiRepository,
) : ViewModel() {

    // Step 0 does not require any uiState
    // as we are not setting any UiState only capturing data in Logcat
    // It will work with Step1 and 2
    val currentRootDetailAsLiveData: MutableLiveData<Poc_RootDetailUiState> = MutableLiveData()

    // Step3 : To make asLiveData and UiState as observable on Ui
    val currentRootDetailAsLiveData1: LiveData<Poc_RootDetailUiState> =
        apiRepository.fetchRootDetailHotFlow(3)
            .map {
                Poc_RootDetailUiState(
                    rootDetail = it.body() ?: RootDetailDto()
                )
            }
            .asLiveData()

    // Step4 : To make ColdFlow using stateIn and collectAsStateWithLifecycle()
    val currentRootDetailAsLiveData2: Flow<Poc_RootDetailUiState> =
        apiRepository.fetchRootDetailHotFlow(3)
            .map {
                Poc_RootDetailUiState(
                    rootDetail = it.body() ?: RootDetailDto()
                )
            }

    // Step5 : To make HotFlow using stateIn and collectAsStateWithLifecycle()
    val currentRootDetailAsLiveData3: Flow<Poc_RootDetailUiState> =
        apiRepository.fetchRootDetailHotFlow(3)
            .map {
                Poc_RootDetailUiState(
                    rootDetail = it.body() ?: RootDetailDto()
                )
            }
            .stateIn(
                scope = viewModelScope,
                //started = SharingStarted.Eagerly
                //started = SharingStarted.Lazily
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = Poc_RootDetailUiState()
            )


    fun getRootDetail() {

        // TODO
        
        /* Step 1:
        viewModelScope.launch {
        //getDogBreedData()
        apiRepository
            .fetchRootDetailsV1(3).collect { rootDetail ->
                val result = rootDetail.body()
                if (rootDetail.isSuccessful && result != null) {
                    Log.d("RAW_FLOW", "Received Item : ${rootDetail.toString()}")
                    // _uiState.value = HomeUiState.Success(dogBreedsList = result.message)
                    currentRootDetailAsLiveData.value = Poc_RootDetailUiState(
                        rootDetail = result
                    )
                }
            }
        }
        */

        // Step 2:
        viewModelScope.launch {
            apiRepository
                .fetchRootDetailsV1(3).onEach { rootDetail ->
                    val result = rootDetail.body()
                    if (rootDetail.isSuccessful && result != null) {
                        Log.d("RAW_FLOW", "Received Item : ${rootDetail.toString()}")
                        // _uiState.value = HomeUiState.Success(dogBreedsList = result.message)
                        currentRootDetailAsLiveData.value = Poc_RootDetailUiState(
                            rootDetail = result
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    init {

        //getRootDetail()

        /* Step : 0
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

        */

    }

}

data class Poc_RootDetailUiState(
    val rootDetail: RootDetailDto = RootDetailDto(),
    val selectedRootId: Int = 3,
    val dateTime: LocalDateTime = LocalDateTime.now()
)