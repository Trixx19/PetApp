package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petapp.data.model.Pet
import com.example.petapp.ui.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    onPetAdded: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    var name by remember { mutableStateOf("") }
    var specie by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adicionar Novo Pet") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Adicionando todos os campos que faltavam
            TextField(
                value = name, onValueChange = { name = it },
                label = { Text("Nome do Pet") }, modifier = Modifier.fillMaxWidth(), singleLine = true
            )
            TextField(
                value = specie, onValueChange = { specie = it },
                label = { Text("Espécie") }, modifier = Modifier.fillMaxWidth(), singleLine = true
            )
            TextField(
                value = sex, onValueChange = { sex = it },
                label = { Text("Sexo") }, modifier = Modifier.fillMaxWidth(), singleLine = true
            )
            TextField(
                value = breed, onValueChange = { breed = it },
                label = { Text("Raça") }, modifier = Modifier.fillMaxWidth(), singleLine = true
            )
            TextField(
                value = birthDate, onValueChange = { birthDate = it },
                label = { Text("Data de Nascimento") }, modifier = Modifier.fillMaxWidth(), singleLine = true
            )
            TextField(
                value = description, onValueChange = { description = it },
                label = { Text("Descrição (Opcional)") }, modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = imageUrl, onValueChange = { imageUrl = it },
                label = { Text("URL da Imagem (Opcional)") }, modifier = Modifier.fillMaxWidth(), singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // ## CORREÇÃO FINAL ##
                    // Criando o objeto 'Pet' com TODOS os campos corretos
                    val newPet = Pet(
                        name = name,
                        specie = specie,
                        sex = sex,
                        breed = breed,
                        birthDate = birthDate,
                        description = description,
                        imageUrl = imageUrl
                    )
                    viewModel.insertPet(newPet)
                    onPetAdded()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SALVAR")
            }
        }
    }
}