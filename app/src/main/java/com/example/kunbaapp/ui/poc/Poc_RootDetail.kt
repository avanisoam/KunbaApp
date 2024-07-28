package com.example.kunbaapp.ui.poc

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.asFlow
import org.koin.androidx.compose.getViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun Poc_RootDetail(
    viewModel: Poc_RootDetailViewModel = getViewModel<Poc_RootDetailViewModel>()
) {
    
    //val uiState by viewModel.currentRootDetailAsLiveData.observeAsState()

    //Text(text = uiState?.rootDetail.toString())
    
    Text(text = "TODO - check response in Logcat")
}