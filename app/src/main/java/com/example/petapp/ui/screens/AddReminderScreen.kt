// app/src/main/java/com/example/petapp/ui/screens/AddReminderScreen.kt
package com.example.petapp.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.data.PetRepository
import com.example.petapp.data.model.Priority
import com.example.petapp.data.model.Reminder
import com.example.petapp.notifications.AlarmScheduler
import com.example.petapp.notifications.NotificationHelper
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.petapp.data.SettingsDataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReminderScreen(petId: Int, navController: NavController) {
    var title by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var priority by remember { mutableStateOf(Priority.LOW) } // Padrão agora é LOW
    var showError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scheduler = AlarmScheduler(context)
    val coroutineScope = rememberCoroutineScope()

    val settingsDataStore = SettingsDataStore(context)
    val notificationsEnabled by settingsDataStore.notificationsEnabled.collectAsState(initial = true)

    // Formatadores para exibir a data e hora
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // Diálogos para seleção de data e hora
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
        },
        selectedDate.year,
        selectedDate.monthValue - 1,
        selectedDate.dayOfMonth
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            selectedTime = LocalTime.of(hour, minute)
        },
        selectedTime.hour,
        selectedTime.minute,
        true // Formato de 24 horas
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Lembrete") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título do Lembrete") },
                modifier = Modifier.fillMaxWidth(),
                isError = showError && title.isBlank()
            )
            if (showError && title.isBlank()) {
                Text("O título é obrigatório", color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))

            // Seletores de Data e Hora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { datePickerDialog.show() }) {
                    Text("Data: ${selectedDate.format(dateFormatter)}")
                }
                Button(onClick = { timePickerDialog.show() }) {
                    Text("Hora: ${selectedTime.format(timeFormatter)}")
                }
            }

            Spacer(Modifier.height(24.dp))

            // Seletor de Prioridade (agora com três opções)
            Text("Prioridade", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Opção Alta
                RadioButton(
                    selected = priority == Priority.HIGH,
                    onClick = { priority = Priority.HIGH }
                )
                Text("Alta", Modifier.clickable { priority = Priority.HIGH })
                Spacer(Modifier.width(16.dp))

                // Opção Média
                RadioButton(
                    selected = priority == Priority.MEDIUM,
                    onClick = { priority = Priority.MEDIUM }
                )
                Text("Média", Modifier.clickable { priority = Priority.MEDIUM })
                Spacer(Modifier.width(16.dp))

                // Opção Baixa
                RadioButton(
                    selected = priority == Priority.LOW,
                    onClick = { priority = Priority.LOW }
                )
                Text("Baixa", Modifier.clickable { priority = Priority.LOW })
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (title.isBlank()) {
                            showError = true
                            return@launch
                        }

                        if (notificationsEnabled) {
                            val dateTime = selectedDate.atTime(selectedTime)
                            val newReminder = Reminder(
                                title = title,
                                dateTime = dateTime,
                                priority = priority
                            )

                            PetRepository.addReminderToPet(petId, newReminder)

                            val channelId = when (priority) { // Lógica para selecionar o canal correto
                                Priority.HIGH -> NotificationHelper.HIGH_PRIORITY_CHANNEL_ID
                                Priority.MEDIUM -> NotificationHelper.MEDIUM_PRIORITY_CHANNEL_ID
                                Priority.LOW -> NotificationHelper.LOW_PRIORITY_CHANNEL_ID
                            }
                            scheduler.schedule(
                                time = dateTime,
                                title = "Lembrete para o seu Pet!",
                                message = title,
                                channelId = channelId
                            )

                            Toast.makeText(context, "Lembrete salvo e agendado!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Toast.makeText(context, "Ative as notificações nas configurações para agendar.", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Lembrete")
            }
        }
    }
}