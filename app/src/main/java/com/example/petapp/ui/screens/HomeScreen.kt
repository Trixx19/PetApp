package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petapp.PetApplication
import com.example.petapp.ui.PetViewModel
import com.example.petapp.ui.PetViewModelFactory
// IMPORTAÇÕES EM FALTA QUE CAUSARAM O ERRO
import com.example.petapp.ui.components.EmptyState
import com.example.petapp.ui.components.PetItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPetClick: (Int) -> Unit,
    viewModel: PetViewModel = viewModel(
        factory = PetViewModelFactory((LocalContext.current.applicationContext as PetApplication).repository)
    )
) {
    val pets by viewModel.allPets.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }
    val filteredPets = pets.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar pet") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(50)),
            shape = RoundedCornerShape(50),
            trailingIcon = { Icon(Icons.Filled.Search, "Busca") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent,
            )
        )

        if (filteredPets.isEmpty()) {
            EmptyState() // Agora vai ser encontrado
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = filteredPets, key = { pet -> pet.id }) { pet ->
                    PetItemCard(pet = pet, onClick = { onPetClick(pet.id) }) // Agora vai ser encontrado
                }
            }
        }
    }
}