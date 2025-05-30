package com.example.petapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petapp.data.PetRepository
import com.example.petapp.data.model.Pet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") } // serve para armazenar a busca
    Scaffold(
        topBar = { // top bar com o nome do app e o menu 3 pontinhos
            TopAppBar(
                title = { Text("Pet App") },
                actions = {
                    MoreOptionsMenu(navController)
                }
            )
        },
        bottomBar = { // bottom bar com navegação
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // campo de busca
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar pet") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                singleLine = true
            )

            // filtra de acordo com o texto
            val filteredPets = PetRepository.petList.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }

            PetList( // mostra a lista dos pets filtrados em relação ao texto 
                pets = filteredPets,
                navController = navController
            )
        }
    }
}


@Composable
fun PetList(pets: List<Pet>, navController: NavController, modifier: Modifier = Modifier) { // composable para criar a lista com cards
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(pets.size) { index ->
            val pet = pets[index]
            PetCard(pet = pet) {
                navController.navigate("details/${pet.id}") // leva para o detalhe do pet específico
            }
        }
    }
}

@Composable
fun PetCard(pet: Pet, onClick: () -> Unit) { // composable para exibir as informações do pet no card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image( // imagem do pet
                painter = painterResource(id = pet.imageRes),
                contentDescription = pet.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) { // informações textuais
                Text(
                    text = pet.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = pet.specie,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = pet.breed,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = pet.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2
                )
            }
            IconButton( // botão de favorito
                onClick = { PetRepository.toggleFavorite(pet.id) }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favoritar",
                    tint = if (pet.isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Composable
fun MoreOptionsMenu(navController: NavController) { // menu 3 pontinhos
    var expanded = remember { mutableStateOf(false) } // estado aberto/fehado
    IconButton(onClick = { expanded.value = true }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Mais opções"
        )
    }
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) { // mostra e leva para as opções de favoritos, configurações e ajuda
        DropdownMenuItem(
            text = { Text("Favoritos") },
            onClick = {
                expanded.value = false
                navController.navigate("favorites")
            }
        )
        DropdownMenuItem(
            text = { Text("Configurações") },
            onClick = {
                expanded.value = false
                navController.navigate("settings")
            }
        )
        DropdownMenuItem(
            text = { Text("Ajuda") },
            onClick = {
                expanded.value = false
                navController.navigate("help")
            }
        )
    }
}
@Composable
fun BottomNavigationBar(navController: NavController) { // composable da bottom bar com a home e favoritos, futuramente perfil também
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
            label = { Text("Início") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("favorites") },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") }
        )
    }
}
