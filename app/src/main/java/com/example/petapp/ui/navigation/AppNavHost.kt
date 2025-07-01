// NAVHOST DO APP (Comentário do seu colega)
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
    // Usamos a sua assinatura de função, com todos os parâmetros de tema
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    currentPalette: ColorPalette,
    onPaletteChange: (ColorPalette) -> Unit
) {
    AnimatedNavHost(navController = navController, startDestination = "home") {
        // algumas tem fade para deixar mais notável a animação de "beat" do ícone (Comentário do seu colega)
        val animationDuration = 700

        // home com fade normal (Comentário do seu colega)
        composable(
            "home",
            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
        ) {
            HomeScreen(navController)
        }

        // adicionar pet com slide vertical (Comentário do seu colega)
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

        // detalhes do pet com slide vertical (Comentário do seu colega)
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

        // favorites com fade normal (Comentário do seu colega)
        composable(
            "favorites",
            enterTransition = { fadeIn(animationSpec = tween(animationDuration)) },
            exitTransition = { fadeOut(animationSpec = tween(animationDuration)) }
        ) {
            FavoritesScreen(navController)
        }

        // configurações com slide vertical (Comentário do seu colega)
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
            // Usamos a sua chamada, que passa todos os parâmetros de tema
            SettingsScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange,
                currentPalette = currentPalette,
                onPaletteChange = onPaletteChange
            )
        }

        // help com slide (Comentário do seu colega)
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

        // lembrete com slide vertical (Comentário do seu colega)
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

        // information - slide vertical (Comentário e tela nova do seu colega)
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
            // Assumindo que você tem um composable InformationScreen(navController)
            // Se não tiver, esta linha dará erro e precisaremos criá-la.
            InformationScreen(navController = navController)
        }
    }
}