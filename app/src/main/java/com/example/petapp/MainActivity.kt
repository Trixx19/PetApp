package com.example.petapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.petapp.ui.theme.PetAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. Acessando as preferências a partir da Application
            val application = application as PetApplication
            val themePreferences = application.themePreferences
            val settingsDataStore = application.settingsDataStore

            // 2. Observando as mudanças em tempo real
            val isDarkMode by settingsDataStore.darkModeEnabled.collectAsState(initial = false)
            val currentTheme by themePreferences.themeVariant.collectAsState(initial = com.example.petapp.ui.theme.ThemeVariant.BLUE_ACCENT)

            // 3. Passando as preferências para o PetAppTheme
            PetAppTheme(
                darkTheme = isDarkMode,
                themeVariant = currentTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PetApp()
                }
            }
        }
    }
}