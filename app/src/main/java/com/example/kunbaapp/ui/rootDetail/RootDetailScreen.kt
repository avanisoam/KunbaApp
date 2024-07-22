package com.example.kunbaapp.ui.rootDetail

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kunbaapp.R
import com.example.kunbaapp.data.models.dto.FamilyDto
import com.example.kunbaapp.data.models.dto.NodeDto
import com.example.kunbaapp.ui.home.HomeDestination
import com.example.kunbaapp.ui.navigation.NavigationDestination
import com.example.kunbaapp.ui.shared.KunbaAppTopBar
import com.example.kunbaapp.ui.shared.RootFamilyItem
import com.example.kunbaapp.ui.shared.RootItem
import com.example.kunbaapp.ui.shared.RootNodeItem
import org.koin.androidx.compose.getViewModel

object RootDetailDestination : NavigationDestination {
    override val route = "rootDetail"
    @StringRes
    override val titleRes = R.string.root_detail
    const val ID_ARG = "rootId"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@Composable
fun RootDetailScreen(
    navigateToFamilyScreen: (Int) -> Unit,
    navigateToNodeScreen: (Int) -> Unit,
    viewModel: RootDetailViewModel = getViewModel<RootDetailViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()
    //Text(text = uiState.rootDetail.toString())
    Scaffold(
        topBar = {
            KunbaAppTopBar(
                canNavigateBack = false,
                title = RootDetailDestination.titleRes
            )
        }
    ){innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        RootDetailBody(
            families = uiState.rootDetail.familyDtos,
            onItemClick = {navigateToFamilyScreen(it)},
            onIndividualClick = {navigateToNodeScreen(it)} ,
            modifier = Modifier.weight(1f)
        )
            Divider()
        NodesBody(
            nodes = uiState.rootDetail.nodeDtos,
            onIndividualClick= {navigateToNodeScreen(it)},
            modifier = Modifier.weight(1f)
            )

        }
    }
}

@Composable
fun RootDetailBody(
    families: List<FamilyDto>,
    onItemClick: (Int) -> Unit,
    onIndividualClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            families.forEach { root ->
                RootFamilyItem(
                    family = root,
                    onItemClick = {onItemClick(root.familyId)},
                    onIndividualClick = {onIndividualClick(it)}
                )

            }
        }
    }
}

@Composable
fun NodesBody(
    nodes: List<NodeDto>,
    onIndividualClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item {
            nodes.forEach { node ->

                RootNodeItem(
                    node = node,
                    onItemClick = {onIndividualClick(it)}
                )
                //Text(text = "${node.firstName} ${node.lastName}")

            }
        }
    }
}