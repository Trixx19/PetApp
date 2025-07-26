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
import com.example.petapp.data.ThemeMode // Importe o enum
import com.example.petapp.ui.SettingsViewModel
import com.example.petapp.ui.theme.ThemeVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
) {
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val selectedThemeMode by viewModel.themeMode.collectAsState() // Estado do modo de tema
    val selectedThemeVariant by viewModel.themeVariant.collectAsState()

    var expandedThemeMenu by remember { mutableStateOf(false) }
    var expandedThemeVariantMenu by remember { mutableStateOf(false) } // Para o seletor de cores
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
                )
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
            Divider()

            // Seletor de Modo de Tema (Claro, Escuro, Automático)
            SettingDropdown(
                label = "Modo do Tema",
                expanded = expandedThemeMenu,
                onExpandedChange = { expandedThemeMenu = it },
                selectedValue = selectedThemeMode.name.replaceFirstChar { it.titlecase() }, // Ex: "System" -> "System"
                options = ThemeMode.values().map { it.name.replaceFirstChar { c -> c.titlecase() } to it },
                onOptionSelected = { viewModel.setThemeMode(it) }
            )

            // Seletor de Cor do Tema
            SettingDropdown(
                label = "Cor do Tema",
                expanded = expandedThemeVariantMenu,
                onExpandedChange = { expandedThemeVariantMenu = it },
                selectedValue = selectedThemeVariant.displayName,
                options = ThemeVariant.values().map { it.displayName to it },
                onOptionSelected = { viewModel.saveThemeVariant(it) }
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> SettingDropdown(
    label: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedValue: String,
    options: List<Pair<String, T>>,
    onOptionSelected: (T) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { (displayName, value) ->
                    DropdownMenuItem(
                        text = { Text(displayName) },
                        onClick = {
                            onOptionSelected(value)
                            onExpandedChange(false)
                        }
                    )
                }
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