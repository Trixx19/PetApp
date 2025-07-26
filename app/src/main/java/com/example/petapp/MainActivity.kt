package com.example.petapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme // Importe esta função
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable // Adicione este import
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.petapp.auth.viewmodel.AuthViewModel
import com.example.petapp.auth.viewmodel.AuthViewModelFactory
import com.example.petapp.data.ThemeMode // Importe o enum
import com.example.petapp.ui.navigation.AppNavHost
import com.example.petapp.ui.theme.PetAppTheme
import com.example.petapp.ui.theme.ThemeVariant

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory((application as PetApplication).authRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val application = application as PetApplication
            val settingsDataStore = application.settingsDataStore
            val themePreferences = application.themePreferences

            // Lê as preferências do DataStore
            val themeMode by settingsDataStore.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
            val currentThemeVariant by themePreferences.themeVariant.collectAsState(initial = ThemeVariant.BLUE_ACCENT)

            // Lógica para determinar se o modo escuro deve ser usado
            val useDarkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme() // Usa a configuração do sistema
            }

            PetAppTheme(
                darkTheme = useDarkTheme,
                themeVariant = currentThemeVariant
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        authViewModel = authViewModel
                    )
                }
            }
        }
    }
}