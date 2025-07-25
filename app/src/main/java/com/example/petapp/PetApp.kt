package com.example.petapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petapp.ui.navigation.AppNavHost
import com.example.petapp.ui.navigation.PetDestinations
import com.example.petapp.ui.screens.MoreOptionsMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val shouldShowMainTopBar = currentRoute == PetDestinations.HOME_ROUTE || currentRoute == PetDestinations.FAVORITES_ROUTE

    Scaffold(
        topBar = {
            if (shouldShowMainTopBar) {
                TopAppBar(
                    title = { Text("PetApp") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    actions = {
                        MoreOptionsMenu(
                            onNavigateToSettings = { navController.navigate(PetDestinations.SETTINGS_ROUTE) },
                            onNavigateToHelp = { navController.navigate(PetDestinations.HELP_ROUTE) }
                        )
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar {
                val currentDestination = navBackStackEntry?.destination

                // Item "Início" com a lógica de navegação correta
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, "Início") },
                    label = { Text(stringResource(R.string.home_destination)) },
                    selected = currentDestination?.hierarchy?.any { it.route == PetDestinations.HOME_ROUTE } == true,
                    onClick = {
                        navController.navigate(PetDestinations.HOME_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                // Item "Favoritos" com a lógica de navegação correta
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, "Favoritos") },
                    label = { Text(stringResource(R.string.favorites_destination)) },
                    selected = currentDestination?.hierarchy?.any { it.route == PetDestinations.FAVORITES_ROUTE } == true,
                    onClick = {
                        navController.navigate(PetDestinations.FAVORITES_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

                // Item "Informações" com a lógica de navegação correta
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Description, "Informações") },
                    label = { Text(stringResource(R.string.information_destination)) },
                    selected = currentDestination?.hierarchy?.any { it.route == PetDestinations.INFORMATION_ROUTE } == true,
                    onClick = {
                        navController.navigate(PetDestinations.INFORMATION_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}