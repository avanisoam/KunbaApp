package com.example.kunbaapp.ui.favorite

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.kunbaapp.R
import com.example.kunbaapp.ui.navigation.NavigationDestination
import com.example.kunbaapp.ui.node.NodeDestination
import com.example.kunbaapp.ui.shared.FavoriteItem
import com.example.kunbaapp.ui.shared.FavoriteItemV1
import com.example.kunbaapp.ui.shared.FilterDropdown
import com.example.kunbaapp.ui.shared.KunbaAppTopBar
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
    navigateUp : () -> Unit,
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
                showFilter = true,
                resetFilter = resetFilter,
            )
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
                            item = item
                        )
                    }
                }
            }
        }
    }
}