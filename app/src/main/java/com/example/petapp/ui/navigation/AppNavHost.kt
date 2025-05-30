package com.example.petapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petapp.ui.screens.*

@Composable
fun AppNavHost( // composable para definir as rotas do app
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    NavHost(navController = navController, startDestination = "home") { // define "home" como rota inicial
        composable("home") {
            HomeScreen(navController)
        }
        composable("favorites") { // leva para a tela de favoritos
            FavoritesScreen(navController)
        }
        composable("settings") { // leva para as configurações
            SettingsScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }
        composable("help") { // leva para a tela de ajuda
            HelpScreen(navController)
        }
        composable("details/{petId}") { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")?.toIntOrNull()
            if (petId != null) { // leva para a tela de detalhes específicos do pet
                PetDetailsScreen(petId, navController)
            } else {
                navController.navigate("home")
            }
        }
    }
}
