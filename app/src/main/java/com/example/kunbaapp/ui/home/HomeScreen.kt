package com.example.kunbaapp.ui.home

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.kunbaapp.R
import com.example.kunbaapp.ui.navigation.NavigationDestination
import org.koin.androidx.compose.getViewModel

object HomeDestination : NavigationDestination {
    override val route = "home"
    @StringRes
    override val titleRes = R.string.kunba_app
}

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel<HomeViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    Text(
        text = uiState.roots.toString()
    )
}