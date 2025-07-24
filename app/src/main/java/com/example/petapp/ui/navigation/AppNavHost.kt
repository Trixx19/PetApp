package com.example.petapp.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.petapp.R
import com.example.petapp.ui.components.BottomNavigationBar
import com.example.petapp.ui.components.MoreOptionsMenu
import com.example.petapp.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.mineicon),
                            contentDescription = "Ícone",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Pet App")
                    }
                },
                actions = { MoreOptionsMenu(navController) }
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            if (currentRoute == "home") {
                FloatingActionButton(onClick = { navController.navigate("add_pet") }) {
                    Icon(Icons.Filled.Add, "Adicionar Pet")
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(onPetClick = { petId -> navController.navigate("details/$petId") })
            }
            // ... (resto das suas rotas)
        }
    }
}