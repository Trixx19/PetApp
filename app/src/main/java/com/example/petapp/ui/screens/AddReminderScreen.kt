package com.example.petapp.ui.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petapp.data.model.Reminder
import com.example.petapp.notifications.AlarmSchedulerImpl
import com.example.petapp.ui.PetViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(
    onReminderAdded: () -> Unit,
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    val pet by viewModel.uiState.collectAsState()
    var title by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scheduler = remember { AlarmSchedulerImpl(context) }
    var selectedDateTime by remember { mutableStateOf<Calendar?>(null) }

    // Lógica para pedir permissão de notificação
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Permissão de notificação negada.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // Lógica da UI para escolher data e hora (simplificada)
    // ... (pode adicionar DatePicker e TimePicker aqui como na versão anterior)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Novo Lembrete para ${pet?.name ?: "..."}",
            style = MaterialTheme.typography.headlineSmall
        )
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título do Lembrete") },
            modifier = Modifier.fillMaxWidth()
        )
        // Adicionar aqui botões para definir a data e hora em 'selectedDateTime'
        Button(onClick = {
            // Lógica para mostrar Date/Time Picker e atualizar selectedDateTime
            // Exemplo:
            selectedDateTime = Calendar.getInstance().apply {
                add(Calendar.SECOND, 15) // Agendar para 15 segundos no futuro para teste
            }
            Toast.makeText(context, "Lembrete agendado para 15 segundos.", Toast.LENGTH_SHORT).show()
        }) {
            Text("Agendar (para teste)")
        }

        Button(
            onClick = {
                // 1. Verificar permissão de notificação (Android 13+)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                    if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        return@Button
                    }
                }

                // 2. Agendar o alarme
                pet?.let { currentPet ->
                    selectedDateTime?.let { calendar ->
                        val newReminder = Reminder(
                            title = title,
                            dateTime = calendar.timeInMillis
                        )
                        scheduler.schedule(newReminder) // Agendar o alarme

                        val updatedReminders = currentPet.reminders + newReminder
                        viewModel.updatePet(currentPet.copy(reminders = updatedReminders))
                        Toast.makeText(context, "Lembrete salvo e agendado!", Toast.LENGTH_SHORT).show()
                        onReminderAdded()
                    } ?: Toast.makeText(context, "Por favor, defina uma data e hora.", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = title.isNotBlank()
        ) {
            Text("Salvar e Agendar Lembrete")
        }
    }
}