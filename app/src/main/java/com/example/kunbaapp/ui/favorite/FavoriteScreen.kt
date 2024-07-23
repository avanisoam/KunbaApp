package com.example.kunbaapp.ui.favorite

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kunbaapp.R
import com.example.kunbaapp.ui.navigation.NavigationDestination
import org.koin.androidx.compose.getViewModel

object FavoriteDestination : NavigationDestination {
    override val route = "favorite"
    @StringRes
    override val titleRes = R.string.node
    //const val ID_ARG = "nodeId"
    //val routeWithArgs = "$route/{$ID_ARG}"
}

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = getViewModel<FavoriteViewModel>()
) {
    Text(text = "Favorite Screen")
}