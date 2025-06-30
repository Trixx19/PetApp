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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState // Importe collectAsState
import androidx.core.content.ContextCompat
import com.example.petapp.data.SettingsDataStore // Importe SettingsDataStore
import com.example.petapp.notifications.NotificationHelper
import com.example.petapp.ui.navigation.AppNavHost
import com.example.petapp.ui.theme.PetAppTheme

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean -> }
    private fun askNotificationPermission() { // permissões para rodar as notificações sem problemas, rezando pra funcionar em todos os dispositivos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
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
            val settingsDataStore = SettingsDataStore(applicationContext)
            val isDarkTheme by settingsDataStore.darkModeEnabled.collectAsState(initial = false)
            PetAppTheme(darkTheme = isDarkTheme) {
                AppNavHost()
            }
        }
    }
}