package com.example.kunbaapp.ui.favorite

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import com.example.kunbaapp.ui.navigation.NavigationDestination
import com.example.kunbaapp.ui.node.NodeDestination
import com.example.kunbaapp.ui.shared.FavoriteItem
import com.example.kunbaapp.ui.shared.FavoriteItemV1
import com.example.kunbaapp.ui.shared.FilterDropdown
import com.example.kunbaapp.ui.shared.KunbaAppTopBar
import com.example.kunbaapp.utils.EntityType
import org.koin.androidx.compose.getViewModel

object FavoriteDestination : NavigationDestination {
    override val route = "favorite"

    @StringRes
    override val titleRes = R.string.node
    const val NAME_ARG = "entity"
    val routeWithArgs = "${FavoriteDestination.route}/{$NAME_ARG}"
}

@Composable
fun FavoriteScreen(
    filterFavorite: (String) -> Unit,
    navigateUp: () -> Unit,
    navigateToNodeScreen: (Int) -> Unit,
    navigateToFamilyScreen: (Int) -> Unit,
    navigateToRootDetailScreen: (Int) -> Unit,
    navigateToHome: () -> Unit,
    resetFilter: () -> Unit,
    viewModel: FavoriteViewModel = getViewModel<FavoriteViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    //Text(text = "Favorite Screen")

    Scaffold(
        topBar = {
            KunbaAppTopBar(
                canNavigateBack = true,
                title = NodeDestination.titleRes,
                navigateUp = navigateUp,
                showFilter = uiState.selectedEntity.isNotEmpty(),
                resetFilter = resetFilter,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToHome,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    //painter = painterResource(id = R.drawable.heart),
                    contentDescription = stringResource(R.string.go_to_favorite)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {

            item {
                FilterDropdown(
                    distinctValues = uiState.availableEntityTypes,
                    filterValue = uiState.selectedEntity.ifEmpty { "Choose Type" },
                    onSelect = { filterFavorite(it) }
                )
            }

            item {
                uiState.favorites?.forEach { item ->
                    if (uiState.selectedEntity.isEmpty() || uiState.selectedEntity == item.type.name) {
                        FavoriteItemV1(
                            item = item,
                            onClick = {
                                when (item.type) {
                                    EntityType.Node -> {
                                        navigateToNodeScreen(it)
                                    }
                                    EntityType.Family -> {
                                        navigateToFamilyScreen(it)
                                    }
                                    EntityType.Root -> {
                                        navigateToRootDetailScreen(it)
                                    }
                                    else -> {
                                        navigateUp()
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}