package com.example.petapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petapp.auth.ui.ForgotPasswordScreen
import com.example.petapp.auth.ui.LoginScreen
import com.example.petapp.auth.ui.RegisterScreen
import com.example.petapp.auth.viewmodel.AuthViewModel
import com.example.petapp.ui.screens.MainAppScreen

// Objeto de rotas sem a SplashScreen
object PetDestinations {
    const val LOGIN_ROUTE = "login"
    const val REGISTER_ROUTE = "register"
    const val FORGOT_PASSWORD_ROUTE = "forgot_password"
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
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    // A lógica de decisão da rota inicial agora está aqui.
    // É uma verificação síncrona, mais simples que a anterior.
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
        // Não há mais rota para SplashScreen
        composable(route = PetDestinations.LOGIN_ROUTE) {
            LoginScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable(route = PetDestinations.REGISTER_ROUTE) {
            RegisterScreen(authViewModel = authViewModel, navController = navController)
        }
        composable(route = PetDestinations.FORGOT_PASSWORD_ROUTE) {
            ForgotPasswordScreen(authViewModel = authViewModel, navController = navController)
        }

        composable(route = PetDestinations.HOME_ROUTE) {
            MainAppScreen(
                onLogout = {
                    authViewModel.logout(navController.context) {
                        // Navegação de volta para a tela de login após o logout
                        navController.navigate(PetDestinations.LOGIN_ROUTE) {
                            popUpTo(PetDestinations.HOME_ROUTE) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}