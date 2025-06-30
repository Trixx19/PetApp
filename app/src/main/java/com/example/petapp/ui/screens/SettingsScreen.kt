// app/src/main/java/com/example/petapp/ui/screens/SettingsScreen.kt
package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.data.PetRepository
import com.example.petapp.data.SettingsDataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    // Removido: isDarkTheme e onThemeChange não são mais passados diretamente aqui
) {
    val context = LocalContext.current
    val settingsDataStore = SettingsDataStore(context)
    val coroutineScope = rememberCoroutineScope()

    // Coleta o estado atual das notificações do DataStore
    val notificationsEnabled by settingsDataStore.notificationsEnabled.collectAsState(initial = true)
    // --- NOVO: Coleta o estado atual do modo escuro do DataStore ---
    val isDarkThemeEnabled by settingsDataStore.darkModeEnabled.collectAsState(initial = false) // Use o valor padrão aqui

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Preferências",
                style = MaterialTheme.typography.titleMedium
            )
            // Interruptor para as notificações
            SettingSwitch(
                title = "Ativar Notificações",
                checked = notificationsEnabled,
                onCheckedChange = { isEnabled ->
                    coroutineScope.launch {
                        settingsDataStore.setNotificationsEnabled(isEnabled)
                    }
                }
            )
            // --- NOVO: Interruptor para o tema escuro usando DataStore ---
            SettingSwitch(
                title = "Ativar Modo Escuro",
                checked = isDarkThemeEnabled, // Usa o estado lido do DataStore
                onCheckedChange = { isEnabled ->
                    coroutineScope.launch {
                        settingsDataStore.setDarkModeEnabled(isEnabled) // Salva no DataStore
                    }
                }
            )
            Divider()
            Button(
                onClick = { PetRepository.clearFavorites() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpar Favoritos")
            }
        }
    }
}

// O Composable do SettingSwitch continua o mesmo.
@Composable
fun SettingSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}