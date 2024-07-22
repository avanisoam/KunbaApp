package com.example.kunbaapp.ui.family

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    viewModel: FamilyViewModel = getViewModel<FamilyViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()

    //Text(text = uiState.family.toString())
    Scaffold(
        topBar = {
            KunbaAppTopBar(
                canNavigateBack = true,
                title = FamilyDestination.titleRes,
                navigateUp = navigateUp
            )
        }
    ) {innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                RootFamilyItem(
                    family = uiState.family,
                    onItemClick = { navigateToFamilyScreen(it) },
                    onIndividualClick = { navigateToNodeScreen(it) }
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