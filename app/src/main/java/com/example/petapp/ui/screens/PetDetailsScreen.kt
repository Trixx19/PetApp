package com.example.petapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.R
import com.example.petapp.data.PetRepository
import com.example.petapp.ui.components.PlaySoundEffect
import com.example.petapp.ui.theme.SuccessGreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetDetailsScreen(petId: Int, navController: NavController) {
    val pet = PetRepository.getPetById(petId)
    val context = LocalContext.current // controla o som atual
    var playSound by remember { mutableStateOf(false) }
    val soundResId = when (pet?.specie?.lowercase()) { // identifica a especie e toca o som de acordo
        "cachorro" -> R.raw.bark
        "gato" -> R.raw.meow
        else -> null
    }
    LaunchedEffect(petId) { // ativa o som quando o usuário abre a tela do pet detalhada
        playSound = true
    }
    soundResId?.let {
        PlaySoundEffect(
            context = context,
            resId = it,
            play = playSound
        )
    }
    Scaffold(
        topBar = { // top bar do detalhe do pet
            TopAppBar(
                title = { Text(pet?.name ?: "Detalhes do Pet") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) { // botão de voltar
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { // botão de favoritar
                        PetRepository.toggleFavorite(petId)
                    }) {
                        Icon(
                            imageVector = if (pet?.isFavorite == true) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorito"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (pet != null) { // exibe o pet se existir
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image( // imagem do pet
                    painter = painterResource(id = pet.imageRes),
                    contentDescription = "Foto do pet",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                // informações principais
                Text(pet.name, style = MaterialTheme.typography.headlineSmall)
                Text(pet.specie, style = MaterialTheme.typography.bodyLarge)
                Text(pet.breed, style = MaterialTheme.typography.bodyLarge)
                Text("Nascimento: ${pet.birthDate}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(pet.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Text( // vacinas do pet
                    "Vacinas",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                if (pet.vaccines.isNotEmpty()) { // percorre o mock das vacinas e exibe cada uma
                    pet.vaccines.forEach { vaccine ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(vaccine.name, style = MaterialTheme.typography.bodyLarge)
                                Text("Data: ${vaccine.date}", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    if (vaccine.isDone) "Status: Completa" else "Status: Pendente",
                                    color = if (vaccine.isDone) SuccessGreen else MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                } else {
                    Text("Nenhuma vacina cadastrada.") // caso nenhuma seja cadastrada
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Text( // compromissos do pet
                    "Compromissos",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                if (pet.appointments.isNotEmpty()) {
                    pet.appointments.forEach { appointment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(appointment.title, style = MaterialTheme.typography.bodyLarge)
                                Text("Data: ${appointment.date}", style = MaterialTheme.typography.bodyMedium)
                                Text(appointment.description, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                } else {
                    Text("Nenhum compromisso cadastrado.")
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Pet não encontrado")
            }
        }
    }
}
