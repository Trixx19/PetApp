package com.example.petapp.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petapp.R
import com.example.petapp.data.model.Appointment
import com.example.petapp.data.model.Pet
import com.example.petapp.data.model.Vaccine
import com.example.petapp.ui.PetViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    onPetAdded: () -> Unit,
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    // --- ESTADOS DA UI ---
    var name by remember { mutableStateOf("") }
    var specie by remember { mutableStateOf("Cachorro") }
    var breed by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("Macho") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("https://i.imgur.com/5J3kL9o.png") }

    // Vacina opcional
    var vaccineName by remember { mutableStateOf("") }

    // Consulta opcional
    var appointmentType by remember { mutableStateOf("") }
    var appointmentLocation by remember { mutableStateOf("") }

    // --- LÓGICA DE DATA E HORA ---
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    // Estado e Dialog para Data de Nascimento
    val initialDate = calendar.time
    var birthDate by remember { mutableStateOf(initialDate) }
    val birthDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance().apply { set(year, month, dayOfMonth) }
            birthDate = cal.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Estado e Dialog para Data da Vacina
    var nextVaccineDate by remember { mutableStateOf<Date?>(null) }
    val vaccineDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance().apply { set(year, month, dayOfMonth) }
            nextVaccineDate = cal.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Estado e Dialog para Data da Consulta
    var appointmentDate by remember { mutableStateOf<Date?>(null) }
    val appointmentDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance().apply { set(year, month, dayOfMonth) }
            appointmentDate = cal.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Estado e Dialog para Hora da Consulta
    var appointmentTime by remember { mutableStateOf<String?>(null) }
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            appointmentTime = String.format("%02d:%02d", hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true // true para formato 24h
    )

    // --- LÓGICA DA BARRA DE PROGRESSO ---
    val progress by remember(name, breed, sex, birthDate) {
        derivedStateOf {
            val totalFields = 4
            var completedFields = 0
            if (name.isNotBlank()) completedFields++
            if (breed.isNotBlank()) completedFields++
            if (sex.isNotBlank()) completedFields++
            if (birthDate != initialDate) completedFields++
            (completedFields.toFloat() / totalFields).coerceIn(0f, 1f)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Novo Pet") },
                navigationIcon = {
                    IconButton(onClick = onPetAdded) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.icondog), contentDescription = "Cachorro", modifier = Modifier.size(80.dp).clip(CircleShape).border(3.dp, if (specie == "Cachorro") MaterialTheme.colorScheme.primary else Color.Gray, CircleShape).clickable { specie = "Cachorro"; imageUrl = "https://i.imgur.com/5J3kL9o.png" })
                    Spacer(Modifier.width(24.dp))
                    Image(painter = painterResource(id = R.drawable.iconcat), contentDescription = "Gato", modifier = Modifier.size(80.dp).clip(CircleShape).border(3.dp, if (specie == "Gato") MaterialTheme.colorScheme.primary else Color.Gray, CircleShape).clickable { specie = "Gato"; imageUrl = "https://i.imgur.com/sS81mK5.png" })
                }
                TextField(value = name, onValueChange = { name = it }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
                TextField(value = breed, onValueChange = { breed = it }, label = { Text("Raça") }, modifier = Modifier.fillMaxWidth())
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Sexo:", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.weight(2f), horizontalArrangement = Arrangement.SpaceAround) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { sex = "Macho" }) { RadioButton(selected = sex == "Macho", onClick = { sex = "Macho" }); Text("Macho") }
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { sex = "Fêmea" }) { RadioButton(selected = sex == "Fêmea", onClick = { sex = "Fêmea" }); Text("Fêmea") }
                    }
                }
                Button(onClick = { birthDatePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Nascimento: ${dateFormatter.format(birthDate)}")
                }
                TextField(value = description, onValueChange = { description = it }, label = { Text("Descrição (Opcional)") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                Text("Adicionar Primeira Vacina (Opcional)", style = MaterialTheme.typography.titleMedium)
                TextField(value = vaccineName, onValueChange = { vaccineName = it }, label = { Text("Nome da Vacina") }, modifier = Modifier.fillMaxWidth())
                Button(onClick = { vaccineDatePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Text(nextVaccineDate?.let { "Próxima Dose: ${dateFormatter.format(it)}" } ?: "Selecionar Data da Próxima Dose")
                }
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                Text("Adicionar Primeira Consulta (Opcional)", style = MaterialTheme.typography.titleMedium)
                TextField(value = appointmentType, onValueChange = { appointmentType = it }, label = { Text("Tipo de Consulta") }, modifier = Modifier.fillMaxWidth())
                Button(onClick = { appointmentDatePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Text(appointmentDate?.let { "Data da Consulta: ${dateFormatter.format(it)}" } ?: "Selecionar Data da Consulta")
                }
                Button(onClick = { timePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Text(appointmentTime?.let { "Hora da Consulta: $it" } ?: "Selecionar Hora da Consulta")
                }
                TextField(value = appointmentLocation, onValueChange = { appointmentLocation = it }, label = { Text("Local") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (name.isBlank() || breed.isBlank()) {
                            Toast.makeText(context, "Nome e Raça são obrigatórios!", Toast.LENGTH_SHORT).show()
                        } else {
                            val vaccinesList = mutableListOf<Vaccine>()
                            if (vaccineName.isNotBlank()) {
                                vaccinesList.add(Vaccine(
                                    name = vaccineName,
                                    date = "N/A (Primeira dose a ser aplicada)",
                                    nextDueDate = nextVaccineDate?.let { dateFormatter.format(it) }
                                ))
                            }

                            val appointmentsList = mutableListOf<Appointment>()
                            if (appointmentType.isNotBlank()) {
                                appointmentsList.add(Appointment(
                                    type = appointmentType,
                                    date = appointmentDate?.let { dateFormatter.format(it) } ?: "N/A",
                                    time = appointmentTime ?: "N/A",
                                    location = appointmentLocation.ifBlank { null }
                                ))
                            }

                            val newPet = Pet(
                                name = name, specie = specie, sex = sex, breed = breed,
                                birthDate = dateFormatter.format(birthDate),
                                description = description, imageUrl = imageUrl,
                                vaccines = vaccinesList, appointments = appointmentsList, reminders = emptyList()
                            )

                            viewModel.insertPet(newPet)
                            Toast.makeText(context, "Pet salvo com sucesso!", Toast.LENGTH_SHORT).show()
                            onPetAdded()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Text("Salvar Pet")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}