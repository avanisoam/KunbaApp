package com.example.kunbaapp.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.ui.navigation.NavigationDestination
import com.example.kunbaapp.ui.shared.RootItem
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
    HomeBody(
        rootList = uiState.roots,
        onItemClick = {}
    )
}

@Composable
fun HomeBody(
    rootList : List<RootRegisterDto>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier) {
        item {
            rootList.forEach { it ->

                RootItem(
                    name = it.rootName,
                    onItemClick = {onItemClick(it)}
                )

            }
        }
    }
}