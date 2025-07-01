// TELA DE CONFIGURAÇÕES
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
import com.example.petapp.data.ThemePreferences
import com.example.petapp.ui.theme.ThemeVariant
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
) {
    val context = LocalContext.current
    val settingsDataStore = SettingsDataStore(context)
    val themePreferences = remember { ThemePreferences(context) }
    val coroutineScope = rememberCoroutineScope()

    // coleta o estado do DataStore
    val notificationsEnabled by settingsDataStore.notificationsEnabled.collectAsState(initial = true)
    val isDarkThemeEnabled by settingsDataStore.darkModeEnabled.collectAsState(initial = false)
    val selectedThemeVariant by themePreferences.themeVariant.collectAsState(initial = ThemeVariant.MONOCHROME)

    var expandedThemeMenu by remember { mutableStateOf(false) }

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
            // switch de notificações
            SettingSwitch(
                title = "Ativar Notificações",
                checked = notificationsEnabled,
                onCheckedChange = { isEnabled ->
                    coroutineScope.launch {
                        settingsDataStore.setNotificationsEnabled(isEnabled)
                    }
                }
            )
            // switch de tema escuro e claro
            SettingSwitch(
                title = "Ativar Modo Escuro",
                checked = isDarkThemeEnabled,
                onCheckedChange = { isEnabled ->
                    coroutineScope.launch {
                        settingsDataStore.setDarkModeEnabled(isEnabled)
                    }
                }
            )
            Divider()

            // seletor de tema
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Escolher Tema", style = MaterialTheme.typography.bodyLarge)
                ExposedDropdownMenuBox(
                    expanded = expandedThemeMenu,
                    onExpandedChange = { expandedThemeMenu = !expandedThemeMenu },
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    OutlinedTextField(
                        value = selectedThemeVariant.displayName, //usa o displayName
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedThemeMenu) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedThemeMenu,
                        onDismissRequest = { expandedThemeMenu = false }
                    ) {
                        ThemeVariant.values().forEach { theme ->
                            DropdownMenuItem(
                                text = { Text(theme.displayName) }, // usa o displayName
                                onClick = {
                                    coroutineScope.launch {
                                        themePreferences.saveThemeVariant(theme)
                                        expandedThemeMenu = false
                                    }
                                }
                            )
                        }
                    }
                }
            }
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