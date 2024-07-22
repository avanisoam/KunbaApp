package com.example.kunbaapp.ui.node

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.kunbaapp.R
import com.example.kunbaapp.ui.navigation.NavigationDestination
import org.koin.androidx.compose.getViewModel

object NodeDestination : NavigationDestination {
    override val route = "node"
    @StringRes
    override val titleRes = R.string.node
    const val ID_ARG = "rootId"
    val routeWithArgs = "$route/{$ID_ARG}"
}

@Composable
fun NodeScreen(
    viewModel: NodeViewModel = getViewModel<NodeViewModel>()
) {
    val uiState by viewModel.uiState.collectAsState()

    Text(text = uiState.node.toString())
}