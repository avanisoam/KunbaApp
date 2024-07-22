package com.example.kunbaapp.ui.node

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
import com.example.kunbaapp.ui.family.FamilyDestination
import com.example.kunbaapp.ui.navigation.NavigationDestination
import com.example.kunbaapp.ui.shared.KunbaAppTopBar
import com.example.kunbaapp.ui.shared.NodeItem
import org.koin.androidx.compose.getViewModel

object NodeDestination : NavigationDestination {
    override val route = "node"
    @StringRes
    override val titleRes = R.string.node
    const val ID_ARG = "nodeId"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@Composable
fun NodeScreen(
    navigateToFamilyScreen: (Int) -> Unit,
    navigateUp: () -> Unit,
    viewModel: NodeViewModel = getViewModel<NodeViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()

    //Text(text = uiState.node.toString())
    Scaffold(
        topBar = {
            KunbaAppTopBar(
                canNavigateBack = true,
                title = NodeDestination.titleRes,
                navigateUp = navigateUp
            )
        }
    ) {innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                NodeItem(
                    node = uiState.node,
                    onItemClick = {navigateToFamilyScreen(it)}
                )
            }

            item {
                Text(text = uiState.node.toString())
            }
        }
    }
}