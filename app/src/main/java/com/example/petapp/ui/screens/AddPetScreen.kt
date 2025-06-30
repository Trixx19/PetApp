// TELA DE ADICIONAR PET NOVO
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
import androidx.navigation.NavController
import com.example.petapp.R
import com.example.petapp.data.PetRepository
import com.example.petapp.data.model.Pet
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var specie by remember { mutableStateOf("Cachorro") } // padrão é cachorro
    var breed by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    val initialBirthDate = remember { LocalDate.now() }
    var birthDate by remember { mutableStateOf(initialBirthDate) }
    var description by remember { mutableStateOf("") }
    var selectedImageRes by remember { mutableStateOf(R.drawable.icon) } // imagem padrão é o icone do app
    val context = LocalContext.current
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            birthDate = LocalDate.of(year, month + 1, dayOfMonth)
        },
        birthDate.year,
        birthDate.monthValue - 1,
        birthDate.dayOfMonth
    )
    // progress bar do novo pet
    val progress by remember {
        derivedStateOf {
            var completedFields = 0
            val totalFields = 4
            if (name.isNotBlank()) completedFields++
            if (breed.isNotBlank()) completedFields++
            if (description.isNotBlank()) completedFields++
            if (birthDate != initialBirthDate) completedFields++
            (completedFields.toFloat() / totalFields).coerceIn(0f, 1f)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Novo Pet") },
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
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) { // icone do pet
                Text("Escolha a espécie do pet:", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.icondog), // cachorro
                    contentDescription = "Ícone de Cachorro",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = if (selectedImageRes == R.drawable.icondog) MaterialTheme.colorScheme.primary else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            selectedImageRes = R.drawable.icondog
                            specie = "Cachorro" // atualiza a especie pela imagem
                        }
                )
                Spacer(Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.iconcat), // gato
                    contentDescription = "Ícone de Gato",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = if (selectedImageRes == R.drawable.iconcat) MaterialTheme.colorScheme.primary else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            selectedImageRes = R.drawable.iconcat
                            specie = "Gato" // atualiza a especie pela imagem
                        }
                )
            }
            Spacer(Modifier.height(8.dp))
            // nome
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome do Pet") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // raça
            OutlinedTextField(
                value = breed,
                onValueChange = { breed = it },
                label = { Text("Raça") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // select do sexo
            Text("Sexo", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = sex == "Macho", onClick = { sex = "Macho" })
                    Text("Macho", Modifier.clickable { sex = "Macho" })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = sex == "Fêmea", onClick = { sex = "Fêmea" })
                    Text("Fêmea", Modifier.clickable { sex = "Fêmea" })
                }
            }
            // data de nascimento
            Button(onClick = { datePickerDialog.show() }) {
                Text("Data de Nascimento: ${birthDate.format(dateFormatter)}")
            }
            // descrição
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (name.isBlank() || breed.isBlank()) {
                        Toast.makeText(context, "Nome e Raça são obrigatórios!", Toast.LENGTH_SHORT).show() // toast de erro
                    } else {
                        val newPet = Pet(
                            id = PetRepository.petList.size + 1, // lista atual + 1, id simples por enquanto
                            name = name,
                            specie = specie,
                            breed = breed,
                            sex = sex,
                            birthDate = birthDate.format(dateFormatter),
                            description = description,
                            imageRes = selectedImageRes,
                            vaccines = listOf(),
                            appointments = listOf(),
                            isFavorite = false,
                            reminders = mutableListOf()
                        )
                        PetRepository.addPet(newPet)
                        Toast.makeText(context, "Pet salvo com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
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