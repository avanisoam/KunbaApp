package com.example.kunbaapp.ui.home

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.RootRegisterDto
import com.example.kunbaapp.ui.navigation.NavigationDestination
import com.example.kunbaapp.ui.shared.KunbaAppTopBar
import com.example.kunbaapp.ui.shared.RootItem
import org.koin.androidx.compose.getViewModel

object HomeDestination : NavigationDestination {
    override val route = "home"
    @StringRes
    override val titleRes = R.string.kunba_app
}

@Composable
fun HomeScreen(
    navigateToDetailScreen: (Int) -> Unit,
    navigateToFavorite: () -> Unit = {},
    viewModel: HomeViewModel = getViewModel<HomeViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            KunbaAppTopBar(
                canNavigateBack = false,
                title = HomeDestination.titleRes
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToFavorite,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    //imageVector = Icons.Filled.Favorite,
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = stringResource(R.string.go_to_favorite)
                )
            }
        }
    ) {innerPadding ->
        HomeBody(
            rootList = uiState.roots,
            onItemClick = {
                Log.d("URL","2# - ${it.toString()}" )
                navigateToDetailScreen(it)
                          },
            toggleFavorite = {viewModel.toggleFavoriteButton(it)},
            favoriteIds = uiState.favoritesRootIds,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    rootList : List<RootRegisterDto>,
    favoriteIds: List<Int>,
    onItemClick: (Int) -> Unit,
    toggleFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            rootList.forEach { root ->

                RootItem(
                    root = root,
                    onItemClick = {
                        Log.d("URL","1# - ${root.rootId.toString()}" )
                        onItemClick(root.rootId)
                    },
                    toggleFavorite = {toggleFavorite(root.rootId)},
                    favoriteIds = favoriteIds

                )

            }
        }
    }
}