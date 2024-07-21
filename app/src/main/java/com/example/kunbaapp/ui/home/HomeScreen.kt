package com.example.kunbaapp.ui.home

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.kunbaapp.R
import com.example.kunbaapp.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    @StringRes
    override val titleRes = R.string.kunba_app
}

@Composable
fun HomeScreen() {
    Text(text = "HomeScreen")
}