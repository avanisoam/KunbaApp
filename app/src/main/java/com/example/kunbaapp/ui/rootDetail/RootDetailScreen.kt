package com.example.kunbaapp.ui.rootDetail

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.kunbaapp.R
import com.example.kunbaapp.ui.navigation.NavigationDestination
import org.koin.androidx.compose.getViewModel

object RootDetailDestination : NavigationDestination {
    override val route = "rootDetail"
    @StringRes
    override val titleRes = R.string.kunba_app
    const val ID_ARG = "rootId"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@Composable
fun RootDetailScreen(
    viewModel: RootDetailViewModel = getViewModel<RootDetailViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Text(text = uiState.rootDetail.toString())
}