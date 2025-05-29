package com.example.petapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petapp.ui.screens.*
import com.example.petapp.ui.screens.FavoritesScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("details/{petId}") { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")?.toIntOrNull()
            if (petId != null) {
                PetDetailsScreen(petId, navController)
            } else {
                navController.navigate("home")
            }
        }
        composable("favorites") {
            FavoritesScreen(navController)
        }
        composable("settings") {
            SettingsScreen(navController)
        }
        composable("help") {
            HelpScreen(navController)
        }
    }
}
