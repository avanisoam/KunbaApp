package com.example.kunbaapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kunbaapp.ui.family.FamilyDestination
import com.example.kunbaapp.ui.family.FamilyScreen
import com.example.kunbaapp.ui.favorite.FavoriteDestination
import com.example.kunbaapp.ui.favorite.FavoriteScreen
import com.example.kunbaapp.ui.home.HomeDestination
import com.example.kunbaapp.ui.home.HomeScreen
import com.example.kunbaapp.ui.node.NodeDestination
import com.example.kunbaapp.ui.node.NodeScreen
import com.example.kunbaapp.ui.rootDetail.RootDetailDestination
import com.example.kunbaapp.ui.rootDetail.RootDetailScreen

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
            HomeScreen(
                navigateToDetailScreen = {
                    Log.d("URL","3# - ${it.toString()}" )
                    navController.navigate("${RootDetailDestination.route}/$it")
                },
                navigateToFavorite = {navController.navigate(FavoriteDestination.route)}
            )
        }

        composable(RootDetailDestination.routeWithArgs,
            arguments = listOf(
                navArgument(RootDetailDestination.ID_ARG) {
                    type = NavType.IntType
                })){
            RootDetailScreen(
                navigateToFamilyScreen = {navController.navigate("${FamilyDestination.route}/$it")},
                navigateToNodeScreen = {navController.navigate("${NodeDestination.route}/$it")},
                navigateUp = {navController.navigate(HomeDestination.route)}
            )
        }

        composable(FamilyDestination.routeWithArgs,
            arguments = listOf(
                navArgument(FamilyDestination.ID_ARG) {
                    type = NavType.IntType
                })
            ){
            FamilyScreen(
                navigateToNodeScreen = {navController.navigate("${NodeDestination.route}/$it")},
                navigateToFamilyScreen = {navController.navigate("${FamilyDestination.route}/$it")},
                navigateUp = {navController.popBackStack()}
            )
        }

        composable(
            NodeDestination.routeWithArgs,
            arguments = listOf(
                navArgument(NodeDestination.ID_ARG) {
                    type = NavType.IntType
                })
        ){
            NodeScreen(
                navigateToFamilyScreen = {navController.navigate("${FamilyDestination.route}/$it")},
                navigateUp = {navController.popBackStack()}
            )
        }

        composable(FavoriteDestination.route){
            FavoriteScreen()
        }
    }
}