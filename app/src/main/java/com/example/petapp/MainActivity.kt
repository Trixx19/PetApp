package com.example.petapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.petapp.data.SettingsDataStore
import com.example.petapp.data.ThemePreferences
import com.example.petapp.ui.screens.*
import com.example.petapp.ui.theme.PetAppTheme
import com.example.petapp.ui.theme.ThemeVariant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsDataStore = SettingsDataStore(applicationContext)
            val isDarkThemeEnabled by settingsDataStore.darkModeEnabled.collectAsState(initial = false)

            // datastore de preferencias do tema
            val themePreferences = ThemePreferences(applicationContext)
            val selectedThemeVariant by themePreferences.themeVariant.collectAsState(initial = ThemeVariant.MONOCHROME)

            PetAppTheme(
                darkTheme = isDarkThemeEnabled,
                themeVariant = selectedThemeVariant
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeScreen(navController = navController) }
                        composable("add_pet") { AddPetScreen(navController = navController) }
                        composable("details/{petId}") { backStackEntry ->
                            val petId = backStackEntry.arguments?.getString("petId")?.toIntOrNull()
                            if (petId != null) {
                                PetDetailsScreen(navController = navController, petId = petId)
                            }
                        }
                        composable("favorites") { FavoritesScreen(navController = navController) }
                        composable("information") { InformationScreen(navController = navController) }
                        composable("settings") { SettingsScreen(navController = navController) }
                        composable("help") { HelpScreen(navController = navController) }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PetAppTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Text("Preview do Pet App")
        }
    }
}