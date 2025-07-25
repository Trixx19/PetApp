package com.example.petapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels // Importar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.petapp.auth.viewmodel.AuthViewModel
import com.example.petapp.auth.viewmodel.AuthViewModelFactory
import com.example.petapp.ui.navigation.AppNavHost
import com.example.petapp.ui.theme.PetAppTheme
import com.example.petapp.ui.theme.ThemeVariant

class MainActivity : ComponentActivity() {

    // Inicializa o AuthViewModel usando a Factory
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory((application as PetApplication).authRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val application = application as PetApplication
            val themePreferences = application.themePreferences
            val settingsDataStore = application.settingsDataStore

            val isDarkMode by settingsDataStore.darkModeEnabled.collectAsState(initial = false)
            val currentTheme by themePreferences.themeVariant.collectAsState(initial = ThemeVariant.BLUE_ACCENT)

            PetAppTheme(
                darkTheme = isDarkMode,
                themeVariant = currentTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // O NavController principal agora vive aqui
                    val navController = rememberNavController()
                    // O AppNavHost agora gerencia toda a navegação do app
                    AppNavHost(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}