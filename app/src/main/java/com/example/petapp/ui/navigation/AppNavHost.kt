// NAVHOST DO APP
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
import com.example.petapp.ui.screens.AddPetScreen
import com.example.petapp.ui.screens.FavoritesScreen
import com.example.petapp.ui.screens.HelpScreen
import com.example.petapp.ui.screens.HomeScreen
import com.example.petapp.ui.screens.PetDetailsScreen
import com.example.petapp.ui.screens.SettingsScreen
import com.example.petapp.ui.screens.InformationScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberAnimatedNavController(),
) {
    AnimatedNavHost(navController = navController, startDestination = "home") {
        // algumas tem fade para deixar mais notável a animação de "beat" do ícone
        val animationDuration = 700
        // home com fade normal
        composable(
            "home",
            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
        ) {
            HomeScreen(navController)
        }
        // adicionar pet com slide vertical
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
        // detalhes do pet com slide vertical
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

        // favorites com fade normal
        composable(
            "favorites",
            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
        ) {
            FavoritesScreen(navController)
        }

        // configurações com slide vertical
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
            )
        }

        // help com slide
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

        // lembrete com slide vertical
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

        // information - slide vertical
        composable(
            "information",
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
            InformationScreen(navController = navController)
        }
    }
}