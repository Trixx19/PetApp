package com.example.petapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.petapp.ui.screens.AddPetScreen
import com.example.petapp.ui.screens.AddReminderScreen
import com.example.petapp.ui.screens.FavoritesScreen
import com.example.petapp.ui.screens.HomeScreen
import com.example.petapp.ui.screens.PetDetailsScreen

object PetDestinations {
    const val HOME_ROUTE = "home"
    const val FAVORITES_ROUTE = "favorites"
    const val ADD_PET_ROUTE = "add_pet"
    const val ADD_REMINDER_ROUTE = "add_reminder"
    const val PET_DETAIL_ROUTE = "pet_detail"
    const val PET_ID_ARG = "petId"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = PetDestinations.HOME_ROUTE,
        modifier = modifier
    ) {
        // ## A CORREÇÃO ESTÁ AQUI ##
        composable(route = PetDestinations.HOME_ROUTE) {
            HomeScreen(
                // A chamada agora está mais simples, sem o onNavigateToFavorites
                onPetClick = { petId ->
                    navController.navigate("${PetDestinations.PET_DETAIL_ROUTE}/$petId")
                },
                onAddPetClick = {
                    navController.navigate(PetDestinations.ADD_PET_ROUTE)
                }
            )
        }

        composable(route = PetDestinations.FAVORITES_ROUTE) {
            FavoritesScreen(
                onPetClick = { petId ->
                    navController.navigate("${PetDestinations.PET_DETAIL_ROUTE}/$petId")
                }
            )
        }

        composable(
            route = "${PetDestinations.PET_DETAIL_ROUTE}/{${PetDestinations.PET_ID_ARG}}",
            arguments = listOf(navArgument(PetDestinations.PET_ID_ARG) { type = NavType.IntType })
        ) {
            PetDetailsScreen(
                onNavigateUp = { navController.popBackStack() },
                onAddReminderClick = { petId ->
                    navController.navigate("${PetDestinations.ADD_REMINDER_ROUTE}/$petId")
                }
            )
        }

        composable(route = PetDestinations.ADD_PET_ROUTE) {
            AddPetScreen(
                onPetAdded = { navController.popBackStack() }
            )
        }

        composable(
            route = "${PetDestinations.ADD_REMINDER_ROUTE}/{${PetDestinations.PET_ID_ARG}}",
            arguments = listOf(navArgument(PetDestinations.PET_ID_ARG) { type = NavType.IntType })
        ) {
            AddReminderScreen(
                onReminderAdded = { navController.popBackStack() }
            )
        }
    }
}