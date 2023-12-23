package com.eventbuilding.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eventbuilding.screens.InternalScreen
import com.eventbuilding.screens.MainScreen
import com.eventbuilding.screens.SavedScreen
import com.eventbuilding.state.ItensListViewModel

@Composable
fun NavGraph (navController: NavHostController, viewModel: ItensListViewModel){
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route)
    {
        composable(route = Screens.Main.route){
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screens.Internal.route){ backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "-"
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: "1"
            InternalScreen(
                viewModel = viewModel,
                categoryName = categoryName,
                categoryId = categoryId
            )
        }
        composable(route = Screens.Saved.route){ backStackEntry ->
            val min = backStackEntry.arguments?.getString("min") ?: "-"
            val max = backStackEntry.arguments?.getString("max") ?: "1"
            SavedScreen(
                minValue = min,
                maxValue = max
            )
        }
    }
}