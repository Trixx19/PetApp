package com.example.petapp.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petapp.PetApplication
import com.example.petapp.data.model.Pet
import com.example.petapp.ui.PetViewModel
import com.example.petapp.ui.PetViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    navController: NavController,
    // Pegamos a mesma instância do ViewModel que a HomeScreen usa
    viewModel: PetViewModel = viewModel(
        factory = PetViewModelFactory((LocalContext.current.applicationContext as PetApplication).repository)
    )
) {
    var name by remember { mutableStateOf("") }
    var specie by remember { mutableStateOf("Cachorro") }
    var breed by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("Macho") }
    var birthDate by remember { mutableStateOf(LocalDate.now()) }
    var description by remember { mutableStateOf("") }

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
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome do Pet") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = breed, onValueChange = { breed = it }, label = { Text("Raça") }, modifier = Modifier.fillMaxWidth())

            // Seletor de Espécie
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                FilterChip(selected = specie == "Cachorro", onClick = { specie = "Cachorro" }, label = { Text("Cachorro") })
                Spacer(Modifier.width(8.dp))
                FilterChip(selected = specie == "Gato", onClick = { specie = "Gato" }, label = { Text("Gato") })
            }

            // Seletor de Sexo
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                FilterChip(selected = sex == "Macho", onClick = { sex = "Macho" }, label = { Text("Macho") })
                Spacer(Modifier.width(8.dp))
                FilterChip(selected = sex == "Fêmea", onClick = { sex = "Fêmea" }, label = { Text("Fêmea") })
            }

            Button(onClick = { datePickerDialog.show() }) {
                Text("Nascimento: ${birthDate.format(dateFormatter)}")
            }

            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (name.isBlank() || breed.isBlank()) {
                        Toast.makeText(context, "Nome e Raça são obrigatórios!", Toast.LENGTH_SHORT).show()
                    } else {
                        val newPet = Pet(
                            name = name,
                            specie = specie,
                            breed = breed,
                            sex = sex,
                            birthDate = birthDate.format(dateFormatter),
                            description = description
                        )
                        // Usamos o ViewModel para inserir o pet no banco de dados!
                        viewModel.insertPet(newPet)
                        Toast.makeText(context, "$name salvo com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Pet")
            }
        }
    }
}