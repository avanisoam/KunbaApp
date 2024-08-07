package com.example.kunbaapp.ui.home

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
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
import com.example.kunbaapp.data.models.dto.V2.RootRegisterDtoV2
import com.example.kunbaapp.ui.family.FamilyUiState
import com.example.kunbaapp.ui.navigation.NavigationDestination
import com.example.kunbaapp.ui.node.NodeUiState
import com.example.kunbaapp.ui.rootDetail.RootDetailUiState
import com.example.kunbaapp.ui.shared.KunbaAppTopBar
import com.example.kunbaapp.ui.shared.RootItem
import com.example.kunbaapp.ui.shared.RootItemV2
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
    val uiStateDb by viewModel.uiStateDb.collectAsState(initial = HomeUiState())
    val uiState by viewModel.uiState.collectAsState()

    /*
    val uiStateDb by viewModel.uiStateDb.collectAsState(initial = HomeUiState())
    //val uiStateFamilyDb by viewModel.uiStateFamilyDb.collectAsState(initial = FamilyUiState())
    val uiStateFamiliesDb by viewModel.uiStateFamiliesDb.collectAsState(initial = FamilyUiState())
    //val uiStateNodesDb by viewModel.uiStateNodesDb.collectAsState(initial = NodeUiState())
    val uiStateRootDetailDb by viewModel.uiStateRootDetailDb.collectAsState(initial = RootDetailUiState())
     */
    Scaffold(
        topBar = {
            KunbaAppTopBar(
                canNavigateBack = false,
                title = HomeDestination.titleRes,
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
        Column(modifier = Modifier.padding(innerPadding)) {
        //LazyColumn(modifier = Modifier.padding(innerPadding)) {
            /*
            item {
                uiStateDb.rootsV2.forEach { root ->

                    root.rootName?.let {
                        Button(onClick = { navigateToDetailScreen(root.id) }) {
                            Text(text = it)
                        }
                    }

                }
            }

             */

                HomeBodyV2(
                    rootListV2 = uiStateDb.rootsV2,//uiState.roots,
                    onItemClick = {
                        Log.d("URL", "2# - ${it.toString()}")
                        navigateToDetailScreen(it)
                    },
                    toggleFavorite = { viewModel.toggleFavoriteButton(it) },
                    favoriteIds = uiState.favoritesRootIds,
                    //modifier = Modifier.padding(innerPadding)
                )

        }
        /*
        Column {


            HomeBody(
                rootList = uiStateDb.roots,//uiState.roots,
                rootListV2 = uiState.rootsV2,
                onItemClick = {
                    Log.d("URL", "2# - ${it.toString()}")
                    navigateToDetailScreen(it)
                },
                toggleFavorite = { viewModel.toggleFavoriteButton(it) },
                favoriteIds = uiState.favoritesRootIds,
                modifier = Modifier.padding(innerPadding)
            )
            //Text(text = uiStateFamilyDb.familyDbo.toString())
            /*
           uiStateFamiliesDb.listOfFamilies.forEach { 
               Text(text = it.toString())
           }

             */
            /*
            uiStateNodesDb.listOfNodesDbo.forEach {
                Text(text = it.toString())
            }

             */

            Text(text = uiStateRootDetailDb.rootDetailDbo.toString())

        }
         */
    }
}

@Composable
fun HomeBody(
    rootList : List<RootRegisterDto>,
    rootListV2 : List<RootRegisterDtoV2>,
    favoriteIds: List<Int>,
    onItemClick: (Int) -> Unit,
    toggleFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            rootListV2.forEach { root ->

                root.rootName?.let { Text(text = it) }

            }
        }
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

@Composable
fun HomeBodyV2(
    rootListV2 : List<RootRegisterDtoV2>,
    favoriteIds: List<Int>,
    onItemClick: (Int) -> Unit,
    toggleFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            rootListV2.forEach { root ->

                RootItemV2(
                    root = root,
                    onItemClick = {
                        Log.d("URL","1# - ${root.id.toString()}" )
                        onItemClick(root.id)
                    },
                    toggleFavorite = {toggleFavorite(root.id)},
                    favoriteIds = favoriteIds

                )

            }
        }
    }
}