package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.petapp.data.model.Appointment
import com.example.petapp.data.model.Pet
import com.example.petapp.data.model.Reminder
import com.example.petapp.data.model.Vaccine
import com.example.petapp.ui.PetViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailsScreen(
    onNavigateUp: () -> Unit,
    onAddReminderClick: (petId: Int) -> Unit, // <-- Definição do parâmetro
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    val pet by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pet?.name ?: "Detalhes do Pet") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    pet?.let { currentPet ->
                        IconToggleButton(
                            checked = currentPet.isFavorite,
                            onCheckedChange = { viewModel.toggleFavoriteStatus(currentPet) }
                        ) {
                            Icon(
                                imageVector = if (currentPet.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favoritar"
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        pet?.let {
            PetDetailsContent(
                pet = it,
                modifier = Modifier.padding(innerPadding),
                onAddReminderClick = { onAddReminderClick(it.id) } // Passa a ação para o content
            )
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun PetDetailsContent(
    pet: Pet,
    modifier: Modifier = Modifier,
    onAddReminderClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Sobre", "Vacinas", "Consultas", "Lembretes")

    Column(modifier = modifier.fillMaxSize()) {
        PetHeader(pet)
        TabRow(selectedTabIndex = selectedTab) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        when (selectedTab) {
            0 -> AboutTab(pet, onAddReminderClick)
            1 -> VaccinesTab(pet.vaccines)
            2 -> AppointmentsTab(pet.appointments)
            3 -> RemindersTab(pet.reminders)
        }
    }
}

@Composable
private fun PetHeader(pet: Pet) {
    Column(modifier = Modifier.padding(16.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pet.imageUrl).crossfade(true).error(android.R.drawable.ic_menu_gallery).build(),
            contentDescription = pet.name,
            modifier = Modifier.fillMaxWidth().height(250.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))
        Text(pet.name, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Text("${pet.specie} | ${pet.breed}", style = MaterialTheme.typography.titleMedium)
        Text("Nascimento: ${pet.birthDate} | Sexo: ${pet.sex}", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun AboutTab(pet: Pet, onAddReminderClick: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Descrição", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(pet.description, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onAddReminderClick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(Icons.Default.AlarmAdd, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text("Adicionar Lembrete")
        }
    }
}

@Composable
private fun VaccinesTab(vaccines: List<Vaccine>) {
    if (vaccines.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text("Nenhuma vacina registada.")
        }
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(vaccines) { vaccine ->
                Card(modifier = Modifier.fillMaxWidth()) { ListItem(headlineContent = { Text(vaccine.name, fontWeight = FontWeight.Bold) }, supportingContent = { Text("Aplicada em: ${vaccine.date}") }) }
            }
        }
    }
}

@Composable
private fun AppointmentsTab(appointments: List<Appointment>) {
    if (appointments.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text("Nenhuma consulta registada.")
        }
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(appointments) { appointment ->
                Card(modifier = Modifier.fillMaxWidth()) { ListItem(headlineContent = { Text(appointment.type, fontWeight = FontWeight.Bold) }, supportingContent = { Text("Data: ${appointment.date} às ${appointment.time}") }) }
            }
        }
    }
}

@Composable
private fun RemindersTab(reminders: List<Reminder>) {
    if (reminders.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
            Text("Nenhum lembrete agendado.")
        }
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(reminders) { reminder ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                        headlineContent = { Text(reminder.title, fontWeight = FontWeight.Bold) },
                        supportingContent = {
                            val sdf = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault())
                            val date = Date(reminder.dateTime)
                            Text("Agendado para: ${sdf.format(date)}")
                        }
                    )
                }
            }
        }
    }
}