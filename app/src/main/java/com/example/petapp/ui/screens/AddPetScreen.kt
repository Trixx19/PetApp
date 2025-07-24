package com.example.petapp.ui.screens

import android.app.DatePickerDialog
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
import com.example.petapp.data.model.Pet
import com.example.petapp.ui.PetViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    onPetAdded: () -> Unit,
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    // --- ESTADOS DA TELA ---
    var name by remember { mutableStateOf("") }
    var specie by remember { mutableStateOf("Cachorro") }
    var breed by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("Macho") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("https://i.imgur.com/5J3kL9o.png") }

    // Estado para a data
    val calendar = Calendar.getInstance()
    val initialDate = calendar.time // Guarda a data inicial para comparar
    var birthDate by remember { mutableStateOf(initialDate) }
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            birthDate = calendar.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // ## LÓGICA DA BARRA DE PROGRESSO ADICIONADA DE VOLTA ##
    val progress by remember(name, breed, sex, birthDate) {
        derivedStateOf {
            val totalFields = 4 // Campos principais: nome, raça, sexo, data de nascimento
            var completedFields = 0
            if (name.isNotBlank()) completedFields++
            if (breed.isNotBlank()) completedFields++
            if (sex.isNotBlank()) completedFields++
            if (birthDate != initialDate) completedFields++ // Progresso se a data foi alterada
            (completedFields.toFloat() / totalFields).coerceIn(0f, 1f)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Novo Pet") },
                navigationIcon = {
                    IconButton(onClick = onPetAdded) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
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
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // ## BARRA DE PROGRESSO ADICIONADA AQUI ##
            LinearProgressIndicator(
                progress = { progress }, // Usando a nova sintaxe do M3
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
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

                // Resto do código continua igual...
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icondog),
                        contentDescription = "Ícone de Cachorro",
                        modifier = Modifier.size(80.dp).clip(CircleShape).border(width = 3.dp, color = if (specie == "Cachorro") MaterialTheme.colorScheme.primary else Color.Gray, shape = CircleShape).clickable {
                            specie = "Cachorro"
                            imageUrl = "https://i.imgur.com/5J3kL9o.png"
                        }
                    )
                    Spacer(Modifier.width(24.dp))
                    Image(
                        painter = painterResource(id = R.drawable.iconcat),
                        contentDescription = "Ícone de Gato",
                        modifier = Modifier.size(80.dp).clip(CircleShape).border(width = 3.dp, color = if (specie == "Gato") MaterialTheme.colorScheme.primary else Color.Gray, shape = CircleShape).clickable {
                            specie = "Gato"
                            imageUrl = "https://i.imgur.com/sS81mK5.png"
                        }
                    )
                }

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do Pet") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                TextField(
                    value = breed,
                    onValueChange = { breed = it },
                    label = { Text("Raça") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Sexo:", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.weight(2f), horizontalArrangement = Arrangement.SpaceAround) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = sex == "Macho", onClick = { sex = "Macho" })
                            Text("Macho", Modifier.clickable { sex = "Macho" })
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = sex == "Fêmea", onClick = { sex = "Fêmea" })
                            Text("Fêmea", Modifier.clickable { sex = "Fêmea" })
                        }
                    }
                }

                Button(onClick = { datePickerDialog.show() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Nascimento: ${dateFormatter.format(birthDate)}")
                }

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição (Opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (name.isBlank() || breed.isBlank()) {
                            Toast.makeText(context, "Nome e Raça são obrigatórios!", Toast.LENGTH_SHORT).show()
                        } else {
                            val newPet = Pet(name = name, specie = specie, sex = sex, breed = breed, birthDate = dateFormatter.format(birthDate), description = description, imageUrl = imageUrl, vaccines = emptyList(), appointments = emptyList(), reminders = emptyList())
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
            }
        }
    }
}