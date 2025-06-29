package com.example.petapp.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.petapp.ui.screens.AddReminderScreen
// Importe a nova tela
import com.example.petapp.ui.screens.AddPetScreen
import com.example.petapp.ui.screens.FavoritesScreen
import com.example.petapp.ui.screens.HelpScreen
import com.example.petapp.ui.screens.HomeScreen
import com.example.petapp.ui.screens.PetDetailsScreen
import com.example.petapp.ui.screens.SettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberAnimatedNavController(),
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    AnimatedNavHost(navController = navController, startDestination = "home") {

        val animationDuration = 700

        // Home Screen - Mantém o fade para a entrada inicial
        composable(
            "home",
            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
        ) {
            HomeScreen(navController)
        }

        // Rota para adicionar novo pet - Animação de swipe de baixo para cima
        composable(
            "add_pet",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeIn(animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))
            }
        ) {
            AddPetScreen(navController = navController)
        }

        // Pet Details Screen - Animação de swipe de baixo para cima
        composable(
            "details/{petId}",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeIn(animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))
            }
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")?.toIntOrNull()
            if (petId != null) {
                PetDetailsScreen(petId, navController)
            } else {
                navController.navigate("home")
            }
        }

        // Favorites Screen - Animação de swipe de baixo para cima
        composable(
            "favorites",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeIn(animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))
            }
        ) {
            FavoritesScreen(navController)
        }

        // Settings Screen - Animação de swipe de baixo para cima
        composable(
            "settings",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeIn(animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))
            }
        ) {
            SettingsScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }

        // Help Screen - Animação de swipe de baixo para cima
        composable(
            "help",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeIn(animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))
            }
        ) {
            HelpScreen(navController)
        }

        // Add Reminder Screen - Animação de swipe de baixo para cima
        composable(
            "add_reminder/{petId}",
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeIn(animationSpec = tween(animationDuration))
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> -fullHeight },
                    animationSpec = tween(animationDuration)
                ) + fadeOut(animationSpec = tween(animationDuration))
            }
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")?.toIntOrNull()
            if (petId != null) {
                AddReminderScreen(petId = petId, navController = navController)
            }
        }
    }
}