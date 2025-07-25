package com.example.petapp.ui.screens

import android.Manifest
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petapp.data.model.Reminder
import com.example.petapp.notifications.AlarmSchedulerImpl // 1. CORREÇÃO: Importando a implementação
import com.example.petapp.ui.PetViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(
    onReminderAdded: () -> Unit,
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    // --- ESTADOS DA UI ---
    var title by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    // --- LÓGICA DE DATA E HORA ---
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var selectedDate by remember { mutableStateOf(calendar.time) }
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedDate = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            calendar.time = selectedDate
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            selectedDate = calendar.time
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    // --- LÓGICA DE NOTIFICAÇÕES ---
    val scheduler = remember { AlarmSchedulerImpl(context) }
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Sem permissão, as notificações não funcionarão.", Toast.LENGTH_LONG).show()
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Lembrete") },
                navigationIcon = {
                    IconButton(onClick = onReminderAdded) {
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
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título do Lembrete") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && title.isBlank(),
                singleLine = true
            )
            if (showError && title.isBlank()) {
                Text("O título é obrigatório", color = MaterialTheme.colorScheme.error)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { datePickerDialog.show() }) {
                    Text("Data: ${dateFormatter.format(selectedDate)}")
                }
                Button(onClick = { timePickerDialog.show() }) {
                    Text("Hora: ${timeFormatter.format(selectedDate)}")
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isBlank()) {
                        showError = true
                        return@Button
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val hasPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
                        if (!hasPermission) {
                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            return@Button
                        }
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                        Toast.makeText(context, "Por favor, ative a permissão para 'Alarmes e lembretes'.", Toast.LENGTH_LONG).show()
                        Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).also { context.startActivity(it) }
                        return@Button
                    }

                    val currentPet = viewModel.uiState.value
                    if (currentPet != null) {
                        // 3. CORREÇÃO: Criando o Reminder sem o campo 'priority'
                        val newReminder = Reminder(
                            title = title,
                            dateTime = selectedDate.time // Salva o tempo em milissegundos (Long)
                        )

                        val updatedReminders = currentPet.reminders + newReminder
                        val updatedPet = currentPet.copy(reminders = updatedReminders)

                        viewModel.updatePet(updatedPet)

                        // 4. CORREÇÃO: Chamando o método 'schedule' correto
                        scheduler.schedule(newReminder)

                        Toast.makeText(context, "Lembrete salvo e agendado!", Toast.LENGTH_SHORT).show()
                        onReminderAdded()
                    } else {
                        Toast.makeText(context, "Erro ao encontrar o pet.", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Lembrete")
            }
        }
    }
}