package com.example.petapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.data.PetRepository
import com.example.petapp.data.SettingsDataStore
// --- IMPORTAÇÃO CORRIGIDA ---
import com.example.petapp.ui.theme.* // Importa tudo do seu pacote de tema, incluindo DefaultThemeColor
// -----------------------------
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    currentPalette: ColorPalette,
    onPaletteChange: (ColorPalette) -> Unit
) {
    val context = LocalContext.current
    val settingsDataStore = SettingsDataStore(context)
    val notificationsEnabled by settingsDataStore.notificationsEnabled.collectAsState(initial = true)
    val coroutineScope = rememberCoroutineScope()

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
            SettingSwitch(
                title = "Ativar Notificações",
                checked = notificationsEnabled,
                onCheckedChange = { isEnabled ->
                    coroutineScope.launch {
                        settingsDataStore.setNotificationsEnabled(isEnabled)
                    }
                }
            )
            SettingSwitch(
                title = "Ativar Modo Escuro",
                checked = isDarkTheme,
                onCheckedChange = onThemeChange
            )
            Divider()

            Text(
                text = "Tema do Aplicativo",
                style = MaterialTheme.typography.titleMedium
            )
            ColorThemeSelector(
                currentPalette = currentPalette,
                onPaletteSelected = { newPalette ->
                    onPaletteChange(newPalette)
                    coroutineScope.launch {
                        settingsDataStore.setThemePalette(newPalette.name)
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

@Composable
fun ColorThemeSelector(
    currentPalette: ColorPalette,
    onPaletteSelected: (ColorPalette) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val colors = mapOf(
            ColorPalette.DEFAULT to DefaultThemeColor,
            ColorPalette.PURPLE to LightPrimary,
            ColorPalette.BLUE to BluePrimary,
            ColorPalette.GREEN to GreenPrimary,
            ColorPalette.PINK to PinkPrimary
        )

        colors.forEach { (palette, color) ->
            ColorOption(
                color = color,
                isSelected = currentPalette == palette,
                onClick = { onPaletteSelected(palette) }
            )
        }
    }
}

@Composable
fun ColorOption(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick)
            .then(
                if (isSelected) {
                    Modifier.border(3.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                } else {
                    Modifier
                }
            )
    )
}