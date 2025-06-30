package com.example.petapp.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.petapp.ui.screens.*
import com.example.petapp.ui.theme.ColorPalette
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberAnimatedNavController(),
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    // Novos parâmetros
    currentPalette: ColorPalette,
    onPaletteChange: (ColorPalette) -> Unit
) {
    AnimatedNavHost(navController = navController, startDestination = "home") {

        val animationDuration = 700

        composable("home", enterTransition = { fadeIn(animationSpec = tween(animationDuration)) }, exitTransition = { fadeOut(animationSpec = tween(animationDuration)) }) {
            HomeScreen(navController)
        }

        composable("add_pet", enterTransition = { slideInVertically(initialOffsetY = { it }, animationSpec = tween(animationDuration)) + fadeIn(animationSpec = tween(animationDuration)) }, exitTransition = { slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(animationDuration)) + fadeOut(animationSpec = tween(animationDuration)) }) {
            AddPetScreen(navController = navController)
        }

        composable("details/{petId}", enterTransition = { slideInVertically(initialOffsetY = { it }, animationSpec = tween(animationDuration)) + fadeIn(animationSpec = tween(animationDuration)) }, exitTransition = { slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(animationDuration)) + fadeOut(animationSpec = tween(animationDuration)) }) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")?.toIntOrNull()
            if (petId != null) {
                PetDetailsScreen(petId, navController)
            } else {
                navController.navigate("home")
            }
        }

        composable("favorites", enterTransition = { slideInVertically(initialOffsetY = { it }, animationSpec = tween(animationDuration)) + fadeIn(animationSpec = tween(animationDuration)) }, exitTransition = { slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(animationDuration)) + fadeOut(animationSpec = tween(animationDuration)) }) {
            FavoritesScreen(navController)
        }

        // --- CHAMADA À SETTINGS SCREEN CORRIGIDA ---
        composable("settings", enterTransition = { slideInVertically(initialOffsetY = { it }, animationSpec = tween(animationDuration)) + fadeIn(animationSpec = tween(animationDuration)) }, exitTransition = { slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(animationDuration)) + fadeOut(animationSpec = tween(animationDuration)) }) {
            SettingsScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange,
                currentPalette = currentPalette, // Passa a paleta atual
                onPaletteChange = onPaletteChange // Passa a função para mudar a paleta
            )
        }
        // ------------------------------------------

        composable("help", enterTransition = { slideInVertically(initialOffsetY = { it }, animationSpec = tween(animationDuration)) + fadeIn(animationSpec = tween(animationDuration)) }, exitTransition = { slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(animationDuration)) + fadeOut(animationSpec = tween(animationDuration)) }) {
            HelpScreen(navController)
        }

        composable("add_reminder/{petId}", enterTransition = { slideInVertically(initialOffsetY = { it }, animationSpec = tween(animationDuration)) + fadeIn(animationSpec = tween(animationDuration)) }, exitTransition = { slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(animationDuration)) + fadeOut(animationSpec = tween(animationDuration)) }) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")?.toIntOrNull()
            if (petId != null) {
                AddReminderScreen(petId = petId, navController = navController)
            }
        }
    }
}