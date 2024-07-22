package com.example.kunbaapp.ui.family

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.kunbaapp.R
import com.example.kunbaapp.ui.navigation.NavigationDestination
import org.koin.androidx.compose.getViewModel

object FamilyDestination : NavigationDestination {
    override val route = "family"
    @StringRes
    override val titleRes = R.string.kunba_app
    const val ID_ARG = "rootId"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@Composable
fun FamilyScreen(
    viewModel: FamilyViewModel = getViewModel<FamilyViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()

    Text(text = uiState.family.toString())
}