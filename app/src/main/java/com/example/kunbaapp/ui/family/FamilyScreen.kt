package com.example.kunbaapp.ui.family

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.R
import com.example.kunbaapp.ui.navigation.NavigationDestination
import com.example.kunbaapp.ui.shared.KunbaAppTopBar
import com.example.kunbaapp.ui.shared.RootFamilyItem
import com.example.kunbaapp.ui.shared.RootNodeItem
import org.koin.androidx.compose.getViewModel

object FamilyDestination : NavigationDestination {
    override val route = "family"
    @StringRes
    override val titleRes = R.string.family
    const val ID_ARG = "rootId"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@Composable
fun FamilyScreen(
    navigateToFamilyScreen: (Int) -> Unit,
    navigateToNodeScreen: (Int) -> Unit,
    navigateUp: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: FamilyViewModel = getViewModel<FamilyViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()

    //Text(text = uiState.family.toString())
    Scaffold(
        topBar = {
            KunbaAppTopBar(
                canNavigateBack = true,
                title = FamilyDestination.titleRes,
                navigateUp = navigateUp,
                isFavorite = uiState.isFavorite,
                toggleFavorite = {viewModel.toggleFavoriteButton(viewModel.familyIdFromUrl)},
                showFavorite = true
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToHome,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    //imageVector = if(uiState.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    imageVector = Icons.Filled.Home,
                    contentDescription = stringResource(R.string.go_to_favorite)
                )
            }
        }
    ) {innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                RootFamilyItem(
                    family = uiState.family,
                    onItemClick = { navigateToFamilyScreen(it) },
                    onIndividualClick = { navigateToNodeScreen(it) },
                )
            }
            item {
                uiState.family.children.forEach { node ->
                    RootNodeItem(
                        node = node,
                        onItemClick = { navigateToNodeScreen(it) },
                        avatar = if(node.gender == 'M') painterResource(id = R.drawable.family_tree_logo) else painterResource(
                            id = R.drawable.woman__1_
                        ),
                        description = if(node.gender == 'M') "Male" else "Female"
                    )
                }
            }
        }
    }
}