package com.example.kunbaapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kunbaapp.ui.navigation.KunbaAppNavGraph

@Composable
fun KunbaAppScreen(
    navController: NavHostController = rememberNavController()
) {
    KunbaAppNavGraph(navController = navController)
}