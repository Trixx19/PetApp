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

@Composable
fun AddPetScreen(
    onPetAdded: () -> Unit,
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    var name by remember { mutableStateOf("") }
    var specie by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Adicionar Novo Pet", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nome do Pet") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = specie, onValueChange = { specie = it }, label = { Text("Espécie (Ex: Cachorro, Gato)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = breed, onValueChange = { breed = it }, label = { Text("Raça") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = sex, onValueChange = { sex = it }, label = { Text("Sexo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = birthDate, onValueChange = { birthDate = it }, label = { Text("Data de Nascimento") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descrição") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("URL da Imagem") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                val newPet = Pet(
                    name = name,
                    specie = specie,
                    breed = breed,
                    sex = sex,
                    birthDate = birthDate,
                    description = description,
                    imageUrl = imageUrl
                )
                viewModel.insertPet(newPet)
                onPetAdded()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank() && specie.isNotBlank()
        ) {
            Text("Salvar Pet")
        }
    }
}