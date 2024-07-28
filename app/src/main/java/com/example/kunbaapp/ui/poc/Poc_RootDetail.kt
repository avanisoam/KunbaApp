package com.example.kunbaapp.ui.poc

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.asFlow
import org.koin.androidx.compose.getViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Poc_RootDetail(
    viewModel: Poc_RootDetailViewModel = getViewModel<Poc_RootDetailViewModel>()
) {
    
    //val uiState by viewModel.currentRootDetailAsLiveData.observeAsState()
    val uiState by viewModel.currentRootDetailAsLiveData1.observeAsState()

    Column {
        // TODO
        // https://stackoverflow.com/questions/75968843/jetpack-compose-format-date-string
        // https://medium.com/mobile-innovation-network/date-formatting-in-compose-multiplatform-a-comprehensive-guide-bb059730afdc
        //Text(text = "lastUpdatedTime: ${LocalDateTime.now().toString()}")

       // Here time is not updated automatically as this ui element is not part of uiState
       // Text(text = "lastUpdatedTime: ${LocalDateTime.now().toString()} - ${uiState?.rootDetail.toString()}")

        // Here time is updated as it is part of Ui State
        Text(text = "lastUpdatedTime: ${uiState?.dateTime} - ${uiState?.rootDetail.toString()}")
    }
    
    //Text(text = "TODO - check response in Logcat")
}