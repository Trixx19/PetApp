package com.example.petapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petapp.ui.navigation.AppNavHost
import com.example.petapp.ui.navigation.PetDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("PetApp") })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == PetDestinations.HOME_ROUTE,
                    onClick = {
                        navController.navigate(PetDestinations.HOME_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = currentRoute == PetDestinations.FAVORITES_ROUTE,
                    onClick = {
                        navController.navigate(PetDestinations.FAVORITES_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Favorite, "Favoritos") },
                    label = { Text("Favoritos") }
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