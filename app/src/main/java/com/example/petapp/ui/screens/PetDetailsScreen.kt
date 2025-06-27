package com.example.petapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.R
import com.example.petapp.data.PetRepository
import com.example.petapp.data.model.Priority
import com.example.petapp.data.model.Reminder
import com.example.petapp.ui.components.PlaySoundEffect
import com.example.petapp.ui.theme.SuccessGreen
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailsScreen(petId: Int, navController: NavController) {
    val pet = PetRepository.getPetById(petId)
    val context = LocalContext.current

    // Lógica para o som (mantida do seu código original)
    var playSound by remember { mutableStateOf(false) }
    val soundResId = when (pet?.specie?.lowercase()) {
        "cachorro" -> R.raw.bark
        "gato" -> R.raw.meow
        else -> null
    }
    LaunchedEffect(petId) {
        playSound = true
    }
    soundResId?.let {
        PlaySoundEffect(context = context, resId = it, play = playSound)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pet?.name ?: "Detalhes do Pet") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { PetRepository.toggleFavorite(petId) }) {
                        Icon(
                            imageVector = if (pet?.isFavorite == true) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorito"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (pet != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Informações do Pet
                Image(
                    painter = painterResource(id = pet.imageRes),
                    contentDescription = "Foto do pet",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(pet.name, style = MaterialTheme.typography.headlineSmall)
                Text(pet.specie, style = MaterialTheme.typography.bodyLarge)
                Text(pet.breed, style = MaterialTheme.typography.bodyLarge)
                Text("Nascimento: ${pet.birthDate}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(pet.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Divider()

                // Seção de Vacinas (Versão original, sem o sino)
                Text(
                    "Vacinas",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                if (pet.vaccines.isNotEmpty()) {
                    pet.vaccines.forEach { vaccine ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(vaccine.name, style = MaterialTheme.typography.bodyLarge)
                                Text("Data: ${vaccine.date}", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    if (vaccine.isDone) "Status: Completa" else "Status: Pendente",
                                    color = if (vaccine.isDone) SuccessGreen else MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                } else {
                    Text("Nenhuma vacina cadastrada.")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider()

                // Seção de Compromissos (Versão original, sem o sino)
                Text(
                    "Compromissos",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                if (pet.appointments.isNotEmpty()) {
                    pet.appointments.forEach { appointment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(appointment.title, style = MaterialTheme.typography.bodyLarge)
                                Text("Data: ${appointment.date}", style = MaterialTheme.typography.bodyMedium)
                                Text(appointment.description, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                } else {
                    Text("Nenhum compromisso cadastrado.")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider()

                // Nova Seção de Lembretes Agendados
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp) // Adiciona o mesmo padding dos outros títulos
                ) {
                    // Título centralizado
                    Text(
                        text = "Lembretes",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    // Botão alinhado à direita
                    IconButton(
                        onClick = { navController.navigate("add_reminder/${pet.id}") },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar Lembrete")
                    }
                }
                if (pet.reminders.isNotEmpty()) {
                    pet.reminders.forEach { reminder ->
                        ReminderCard(reminder = reminder)
                    }
                } else {
                    Text("Nenhum lembrete agendado.")
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Pet não encontrado")
            }
        }
    }
}

// Card para exibir os lembretes criados
@Composable
fun ReminderCard(reminder: Reminder) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (reminder.priority == Priority.HIGH)
                MaterialTheme.colorScheme.tertiaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(reminder.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text("Agendado para: ${reminder.dateTime.format(formatter)}", style = MaterialTheme.typography.bodyMedium)
            Text("Prioridade: ${if (reminder.priority == Priority.HIGH) "Alta" else "Baixa"}", style = MaterialTheme.typography.bodySmall)
        }
    }
}