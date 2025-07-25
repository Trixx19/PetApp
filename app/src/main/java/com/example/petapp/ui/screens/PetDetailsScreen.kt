package com.example.petapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AlarmAdd
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.petapp.R
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
    onAddReminderClick: (petId: Int) -> Unit,
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
                modifier = Modifier.height(74.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
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
                onAddReminderClick = { onAddReminderClick(it.id) }
            )
        } ?: Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageModifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))

        when (pet.imageUrl) {
            "local_dog" -> Image(
                painter = painterResource(R.drawable.icondog),
                contentDescription = "Cachorro",
                modifier = imageModifier.padding(32.dp),
                contentScale = ContentScale.Fit
            )
            "local_cat" -> Image(
                painter = painterResource(R.drawable.iconcat),
                contentDescription = "Gato",
                modifier = imageModifier.padding(32.dp),
                contentScale = ContentScale.Fit
            )
            else -> AsyncImage(
                model = Uri.parse(pet.imageUrl),
                contentDescription = pet.name,
                modifier = imageModifier,
                contentScale = ContentScale.Crop,
                error = painterResource(if (pet.specie == "Cachorro") R.drawable.icondog else R.drawable.iconcat)
            )
        }

        Spacer(Modifier.height(16.dp))
        Text(pet.name, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Text("${pet.specie} | ${pet.breed}", style = MaterialTheme.typography.titleMedium)
        Text("Nascimento: ${pet.birthDate} | Sexo: ${pet.sex}", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun AboutTab(pet: Pet, onAddReminderClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        if (pet.description.isNotBlank()) {
            Text("Descrição", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text(pet.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(24.dp))
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
                Card(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                        headlineContent = { Text(vaccine.name, fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("Aplicada em: ${vaccine.date}\nPróxima dose: ${vaccine.nextDueDate ?: "Não definida"}") }
                    )
                }
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
                Card(modifier = Modifier.fillMaxWidth()) {
                    ListItem(
                        headlineContent = { Text(appointment.type, fontWeight = FontWeight.Bold) },
                        supportingContent = { Text("Data: ${appointment.date} às ${appointment.time}\nLocal: ${appointment.location ?: "Não definido"}") }
                    )
                }
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

        Button(onClick = onAddReminderClick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(Icons.Default.AlarmAdd, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
            Text("Adicionar Lembrete")
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