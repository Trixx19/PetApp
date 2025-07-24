package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petapp.PetApplication
import com.example.petapp.data.model.Pet
import com.example.petapp.ui.PetViewModel
import com.example.petapp.ui.PetViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailsScreen(
    petId: Int,
    navController: NavController,
    viewModel: PetViewModel = viewModel(
        factory = PetViewModelFactory((LocalContext.current.applicationContext as PetApplication).repository)
    )
) {
    val pet by viewModel.getPetById(petId).collectAsStateWithLifecycle(initialValue = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = pet?.name ?: "Carregando...") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    pet?.let {
                        IconButton(onClick = { viewModel.toggleFavorite(it) }) {
                            Icon(
                                imageVector = if (it.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favoritar",
                                tint = if (it.isFavorite) Color.Red else LocalContentColor.current
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (pet == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            PetDetailsContent(pet!!, Modifier.padding(padding))
        }
    }
}

@Composable
fun PetDetailsContent(pet: Pet, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Pets,
            contentDescription = "Ícone do Pet",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = pet.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        PetInfoRow(label = "Espécie", value = pet.specie)
        PetInfoRow(label = "Raça", value = pet.breed)
        PetInfoRow(label = "Sexo", value = pet.sex)
        PetInfoRow(label = "Data de Nascimento", value = pet.birthDate)
        if (pet.description.isNotBlank()) {
            PetInfoRow(label = "Descrição", value = pet.description)
        }
    }
}

@Composable
fun PetInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(0.6f)
        )
    }
}