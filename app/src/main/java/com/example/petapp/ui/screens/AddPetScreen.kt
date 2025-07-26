package com.example.petapp.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.petapp.R
import com.example.petapp.data.model.Appointment
import com.example.petapp.data.model.Pet
import com.example.petapp.data.model.Vaccine
import com.example.petapp.ui.PetViewModel
import kotlinx.coroutines.launch
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
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var vaccineName by remember { mutableStateOf("") }
    var vaccineIsDone by remember { mutableStateOf(false) }
    var appointmentType by remember { mutableStateOf("") }
    var appointmentLocation by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // --- LÓGICA DE DATA E HORA ---
    val calendar = Calendar.getInstance()
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val initialDate = calendar.time
    var birthDate by remember { mutableStateOf(initialDate) }
    var nextVaccineDate by remember { mutableStateOf<Date?>(null) }
    var appointmentDate by remember { mutableStateOf<Date?>(null) }
    var appointmentTime by remember { mutableStateOf<String?>(null) }
    var vaccineAppliedDate by remember { mutableStateOf<Date?>(null) }

    // Dialogs
    val birthDatePickerDialog = DatePickerDialog(context, { _, y, m, d -> val c = Calendar.getInstance().apply { set(y, m, d) }; birthDate = c.time }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    val vaccineDatePickerDialog = DatePickerDialog(context, { _, y, m, d -> val c = Calendar.getInstance().apply { set(y, m, d) }; nextVaccineDate = c.time }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    val appointmentDatePickerDialog = DatePickerDialog(context, { _, y, m, d -> val c = Calendar.getInstance().apply { set(y, m, d) }; appointmentDate = c.time }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    val timePickerDialog = TimePickerDialog(context, { _, hour, minute -> appointmentTime = String.format("%02d:%02d", hour, minute) }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
    val vaccineAppliedDatePickerDialog = DatePickerDialog(context, { _, y, m, d -> val c = Calendar.getInstance().apply { set(y, m, d) }; vaccineAppliedDate = c.time }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

    // Launcher da Galeria
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> uri?.let { imageUri = it } }
    )

    // Lógica da Barra de Progresso
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
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth().height(8.dp), color = MaterialTheme.colorScheme.primary, trackColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
            Column(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.size(150.dp).clip(CircleShape).border(3.dp, MaterialTheme.colorScheme.primary, CircleShape).clickable { galleryLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        AsyncImage(model = imageUri, contentDescription = "Imagem do Pet", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = if (specie == "Cachorro") R.drawable.icondog else R.drawable.iconcat),
                                contentDescription = specie,
                                modifier = Modifier.size(80.dp)
                            )
                            Text(text = "Escolher foto", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Espécie:", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.weight(2f), horizontalArrangement = Arrangement.SpaceAround) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { specie = "Cachorro" }) { RadioButton(selected = specie == "Cachorro", onClick = { specie = "Cachorro" }); Text("Cachorro") }
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { specie = "Gato" }) { RadioButton(selected = specie == "Gato", onClick = { specie = "Gato" }); Text("Gato") }
                    }
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = vaccineIsDone,
                        onCheckedChange = { vaccineIsDone = it }
                    )
                    Text("Vacina já foi aplicada")
                }

                if (vaccineIsDone) {
                    Button(onClick = { vaccineAppliedDatePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                        Text(vaccineAppliedDate?.let { "Data de Aplicação: ${dateFormatter.format(it)}" } ?: "Selecionar Data de Aplicação")
                    }
                }

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
                            isLoading = true
                            coroutineScope.launch {
                                // 1. SALVA A IMAGEM LOCALMENTE
                                val imagePath = if (imageUri != null) {
                                    viewModel.saveImageLocally(imageUri!!)
                                } else {
                                    if (specie == "Cachorro") "local_dog" else "local_cat"
                                }

                                if (imagePath == null && imageUri != null) {
                                    Toast.makeText(context, "Falha ao salvar a imagem. Tente novamente.", Toast.LENGTH_LONG).show()
                                    isLoading = false
                                    return@launch
                                }

                                // 2. MONTA O OBJETO PET com o caminho local da imagem
                                val vaccinesList = mutableListOf<Vaccine>()
                                if (vaccineName.isNotBlank()) {
                                    val dateApplied = if (vaccineIsDone) {
                                        vaccineAppliedDate?.let { dateFormatter.format(it) } ?: "N/A"
                                    } else {
                                        "Pendente"
                                    }
                                    vaccinesList.add(Vaccine(name = vaccineName, date = dateApplied, nextDueDate = nextVaccineDate?.let { dateFormatter.format(it) }))
                                }
                                val appointmentsList = mutableListOf<Appointment>()
                                if (appointmentType.isNotBlank()) {
                                    appointmentsList.add(Appointment(type = appointmentType, date = appointmentDate?.let { dateFormatter.format(it) } ?: "N/A", time = appointmentTime ?: "N/A", location = appointmentLocation.ifBlank { null }))
                                }

                                val newPet = Pet(
                                    name = name,
                                    specie = specie,
                                    sex = sex,
                                    breed = breed,
                                    birthDate = dateFormatter.format(birthDate),
                                    description = description,
                                    imageUrl = imagePath ?: (if (specie == "Cachorro") "local_dog" else "local_cat"),
                                    vaccines = vaccinesList,
                                    appointments = appointmentsList,
                                    reminders = emptyList()
                                )

                                // 3. INSERE O PET NO BANCO DE DADOS
                                viewModel.insertPet(newPet)

                                isLoading = false
                                Toast.makeText(context, "Pet salvo com sucesso!", Toast.LENGTH_SHORT).show()
                                onPetAdded()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    contentPadding = PaddingValues(16.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        Text("Salvar Pet")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}