package com.example.kunbaapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kunbaapp.ui.home.HomeDestination
import com.example.kunbaapp.ui.home.HomeScreen

@Composable
fun KunbaAppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ){
        composable(HomeDestination.route){
            HomeScreen()
        }
    }
}