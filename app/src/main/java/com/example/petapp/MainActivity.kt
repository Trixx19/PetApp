package com.example.petapp

import android.Manifest
import android.app.AlarmManager
import android.content.Intent // <<<--- A LINHA QUE FALTAVA
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
import androidx.core.content.ContextCompat
import com.example.petapp.notifications.NotificationHelper
import com.example.petapp.ui.navigation.AppNavHost
import com.example.petapp.ui.theme.PetAppTheme

class MainActivity : ComponentActivity() {

    // Lida com o pedido de permissão de notificação
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Aqui você pode adicionar lógica caso a permissão seja concedida ou negada
    }

    private fun askNotificationPermission() {
        // Permissão para Notificações (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // Permissão para Alarmes Exatos (Android 12+)
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

        // Cria os canais de notificação assim que o app é iniciado
        val notificationHelper = NotificationHelper(this)
        notificationHelper.createNotificationChannels()

        // Pede a permissão
        askNotificationPermission()

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) } // estado que controla o tema (local)
            PetAppTheme(darkTheme = isDarkTheme) {
                AppNavHost( // informa o tema atual e troca para o outro
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = it }
                )
            }
        }
    }
}