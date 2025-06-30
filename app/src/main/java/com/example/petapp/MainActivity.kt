package com.example.petapp

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import com.example.petapp.data.SettingsDataStore
import com.example.petapp.notifications.NotificationHelper
import com.example.petapp.ui.navigation.AppNavHost
import com.example.petapp.ui.theme.ColorPalette
import com.example.petapp.ui.theme.PetAppTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* Não é necessário fazer nada aqui por enquanto */ }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent().also { intent ->
                    intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    startActivity(intent)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notificationHelper = NotificationHelper(this)
        notificationHelper.createNotificationChannels()
        askNotificationPermission()

        setContent {
            val settingsDataStore = remember { SettingsDataStore(this) }
            var isDarkTheme by remember { mutableStateOf(false) }

            // O estado da paleta de cores começa com o tema padrão.
            var currentPalette by remember { mutableStateOf(ColorPalette.DEFAULT) }

            // Efeito que lê a preferência guardada quando a app abre.
            LaunchedEffect(key1 = settingsDataStore) {
                launch {
                    val savedPaletteName = settingsDataStore.themePalette.first()
                    // Tenta converter o nome guardado para uma paleta, se falhar, usa o padrão.
                    currentPalette = try {
                        ColorPalette.valueOf(savedPaletteName)
                    } catch (e: IllegalArgumentException) {
                        ColorPalette.DEFAULT
                    }
                }
            }

            // Aplica o tema com base nos estados atuais.
            PetAppTheme(
                darkTheme = isDarkTheme,
                colorPalette = currentPalette
            ) {
                // Passa os estados e as funções para o NavHost controlar a navegação e as mudanças de tema.
                AppNavHost(
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = it },
                    currentPalette = currentPalette,
                    onPaletteChange = { newPalette ->
                        currentPalette = newPalette
                    }
                )
            }
        }
    }
}