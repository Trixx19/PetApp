package com.example.petapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petapp.ui.SettingsViewModel
import com.example.petapp.ui.theme.ThemeVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
) {
    // Coleta o estado do ViewModel
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val isDarkThemeEnabled by viewModel.darkModeEnabled.collectAsState()
    val selectedThemeVariant by viewModel.themeVariant.collectAsState()

    var expandedThemeMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.height(74.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Preferências", style = MaterialTheme.typography.titleMedium)

            SettingSwitch(
                title = "Ativar Notificações",
                checked = notificationsEnabled,
                onCheckedChange = { viewModel.setNotificationsEnabled(it) }
            )
            SettingSwitch(
                title = "Ativar Modo Escuro",
                checked = isDarkThemeEnabled,
                onCheckedChange = { viewModel.setDarkModeEnabled(it) }
            )
            Divider()

            // Seletor de Tema
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tema do Aplicativo:", style = MaterialTheme.typography.bodyLarge)
                ExposedDropdownMenuBox(
                    expanded = expandedThemeMenu,
                    onExpandedChange = { expandedThemeMenu = !expandedThemeMenu }
                ) {
                    OutlinedTextField(
                        value = selectedThemeVariant.displayName,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedThemeMenu) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedThemeMenu,
                        onDismissRequest = { expandedThemeMenu = false }
                    ) {
                        ThemeVariant.values().forEach { theme ->
                            DropdownMenuItem(
                                text = { Text(theme.displayName) },
                                onClick = {
                                    viewModel.saveThemeVariant(theme)
                                    expandedThemeMenu = false
                                }
                            )
                        }
                    }
                }
            }
            Divider()

            Button(
                onClick = {
                    viewModel.clearFavorites()
                    Toast.makeText(context, "Favoritos limpos!", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpar Favoritos")
            }
        }
    }
}

@Composable
private fun SettingSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}