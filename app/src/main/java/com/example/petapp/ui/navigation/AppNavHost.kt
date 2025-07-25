package com.example.petapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.petapp.auth.ui.ForgotPasswordScreen
import com.example.petapp.auth.ui.LoginScreen
import com.example.petapp.auth.ui.RegisterScreen
import com.example.petapp.auth.viewmodel.AuthViewModel
import com.example.petapp.ui.screens.*

object PetDestinations {
    // Rotas de Autenticação
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val FORGOT_PASSWORD_ROUTE = "forgot_password"

    // Rotas do App Principal
    const val HOME_ROUTE = "home"
    const val FAVORITES_ROUTE = "favorites"
    const val ADD_PET_ROUTE = "add_pet"
    const val ADD_REMINDER_ROUTE = "add_reminder"
    const val PET_DETAIL_ROUTE = "pet_detail"
    const val SETTINGS_ROUTE = "settings"
    const val HELP_ROUTE = "help"
    const val INFORMATION_ROUTE = "information"
    const val PET_ID_ARG = "petId"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel, // Recebe o AuthViewModel
    modifier: Modifier = Modifier
) {
    // Define a rota inicial baseada no status de login do usuário
    val startDestination = if (authViewModel.isUserLoggedIn()) {
        PetDestinations.HOME_ROUTE
    } else {
        PetDestinations.LOGIN_ROUTE
    }

    NavHost(
        navController = navController,
        startDestination = startDestination, // Rota inicial dinâmica
        modifier = modifier
    ) {
        // --- GRAFO DE AUTENTICAÇÃO ---
        composable(route = PetDestinations.LOGIN_ROUTE) {
            LoginScreen(
                authViewModel = authViewModel,
                navController = navController,
                onLoginSuccess = {
                    // Navega para a home e limpa a pilha de navegação
                    navController.navigate(PetDestinations.HOME_ROUTE) {
                        popUpTo(PetDestinations.LOGIN_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(route = PetDestinations.REGISTER_ROUTE) {
            RegisterScreen(authViewModel = authViewModel, navController = navController)
        }
        composable(route = PetDestinations.FORGOT_PASSWORD_ROUTE) {
            ForgotPasswordScreen(authViewModel = authViewModel, navController = navController)
        }


        // --- GRAFO DO APP PRINCIPAL ---
        composable(route = PetDestinations.HOME_ROUTE) {
            // A HomeScreen agora faz parte de um fluxo autenticado
            MainAppScreen(
                onLogout = {
                    authViewModel.logout(navController.context) {
                        navController.navigate(PetDestinations.LOGIN_ROUTE) {
                            popUpTo(PetDestinations.HOME_ROUTE) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}