package com.example.petapp.ui.screens

// IMPORTAÇÕES ADICIONADAS PARA CORRIGIR OS ERROS
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert // Import para o ícone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf // Import para mutableStateOf
import androidx.compose.runtime.remember // Import para remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp // Import para o 'dp'
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType // Import para o NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.petapp.R
import com.example.petapp.ui.navigation.PetDestinations
import androidx.compose.material.icons.rounded.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(onLogout: () -> Unit) {
    val navController: NavHostController = rememberNavController() // Tipo explícito para clareza
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Rotas que devem mostrar a TopBar principal
    val shouldShowMainTopBar = currentRoute in listOf(
        PetDestinations.HOME_ROUTE,
        PetDestinations.FAVORITES_ROUTE
    )

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
                    modifier = Modifier.height(64.dp),
                    actions = {
                        MoreOptionsMenu(
                            onNavigateToSettings = { navController.navigate(PetDestinations.SETTINGS_ROUTE) },
                            onNavigateToHelp = { navController.navigate(PetDestinations.HELP_ROUTE) },
                            onLogout = onLogout
                        )
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar {
                val currentDestination = navBackStackEntry?.destination

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, "Início") },
                    label = { Text(stringResource(R.string.home_destination)) },
                    selected = currentDestination?.hierarchy?.any { it.route == PetDestinations.HOME_ROUTE } == true,
                    onClick = {
                        navController.navigate(PetDestinations.HOME_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, "Favoritos") },
                    label = { Text(stringResource(R.string.favorites_destination)) },
                    selected = currentDestination?.hierarchy?.any { it.route == PetDestinations.FAVORITES_ROUTE } == true,
                    onClick = {
                        navController.navigate(PetDestinations.FAVORITES_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Description, "Informações") },
                    label = { Text(stringResource(R.string.information_destination)) },
                    selected = currentDestination?.hierarchy?.any { it.route == PetDestinations.INFORMATION_ROUTE } == true,
                    onClick = {
                        navController.navigate(PetDestinations.INFORMATION_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        // Este NavHost controla apenas a navegação interna do app principal
        NavHost(
            navController = navController,
            startDestination = PetDestinations.HOME_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = PetDestinations.HOME_ROUTE) {
                HomeScreen(
                    onPetClick = { petId -> navController.navigate("${PetDestinations.PET_DETAIL_ROUTE}/$petId") },
                    onAddPetClick = { navController.navigate(PetDestinations.ADD_PET_ROUTE) }
                )
            }
            composable(route = PetDestinations.FAVORITES_ROUTE) {
                FavoritesScreen(
                    onPetClick = { petId -> navController.navigate("${PetDestinations.PET_DETAIL_ROUTE}/$petId") }
                )
            }
            composable(
                route = "${PetDestinations.PET_DETAIL_ROUTE}/{${PetDestinations.PET_ID_ARG}}",
                arguments = listOf(navArgument(PetDestinations.PET_ID_ARG) { type = NavType.IntType })
            ) {
                PetDetailsScreen(
                    onNavigateUp = { navController.popBackStack() },
                    onAddReminderClick = { petId -> navController.navigate("${PetDestinations.ADD_REMINDER_ROUTE}/$petId") }
                )
            }
            composable(route = PetDestinations.ADD_PET_ROUTE) {
                AddPetScreen(onPetAdded = { navController.popBackStack() })
            }
            composable(
                route = "${PetDestinations.ADD_REMINDER_ROUTE}/{${PetDestinations.PET_ID_ARG}}",
                arguments = listOf(navArgument(PetDestinations.PET_ID_ARG) { type = NavType.IntType })
            ) {
                AddReminderScreen(onReminderAdded = { navController.popBackStack() })
            }
            composable(route = PetDestinations.SETTINGS_ROUTE) {
                SettingsScreen(onNavigateUp = { navController.popBackStack() })
            }
            composable(route = PetDestinations.HELP_ROUTE) {
                HelpScreen(navController = navController)
            }
            composable(route = PetDestinations.INFORMATION_ROUTE) {
                InformationScreen(onNavigateUp = { navController.popBackStack() })
            }
        }
    }
}


@Composable
fun MoreOptionsMenu(
    onNavigateToSettings: () -> Unit,
    onNavigateToHelp: () -> Unit,
    onLogout: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        // 2. ÍCONE COM MAIS PERSONALIDADE
        Icon(Icons.Rounded.MoreVert, contentDescription = "Mais opções")
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Configurações") },
            // 3. ÍCONE ARREDONDADO
            leadingIcon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
            onClick = {
                expanded = false
                onNavigateToSettings()
            }
        )
        DropdownMenuItem(
            text = { Text("Ajuda") },
            // 4. ÍCONE ARREDONDADO
            leadingIcon = { Icon(Icons.Rounded.HelpOutline, contentDescription = null) },
            onClick = {
                expanded = false
                onNavigateToHelp()
            }
        )
        Divider()
        DropdownMenuItem(
            text = { Text("Sair") },
            // 5. ÍCONE ARREDONDADO
            leadingIcon = { Icon(Icons.Rounded.Logout, contentDescription = "Sair") },
            onClick = {
                expanded = false
                onLogout()
            }
        )
    }
}