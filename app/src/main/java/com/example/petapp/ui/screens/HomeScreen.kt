package com.example.petapp.ui.screens

import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.petapp.R
import com.example.petapp.data.model.Pet
import com.example.petapp.ui.PetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPetClick: (Int) -> Unit,
    onAddPetClick: () -> Unit,
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    val allPets by viewModel.allPets.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredPets = if (searchQuery.isBlank()) {
        allPets
    } else {
        allPets.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPetClick) {
                Icon(Icons.Filled.Add, contentDescription = "Adicionar Novo Pet")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
            PetList(
                pets = filteredPets,
                onPetClick = onPetClick,
                onToggleFavorite = { pet -> viewModel.toggleFavoriteStatus(pet) }
            )
        }
    }
}

@Composable
private fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Buscar pet") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(50),
        singleLine = true,
        trailingIcon = { Icon(Icons.Filled.Search, "Buscar") },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}

@Composable
private fun PetList(
    pets: List<Pet>,
    onPetClick: (Int) -> Unit,
    onToggleFavorite: (Pet) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(pets, key = { it.id }) { pet ->
            PetCard(
                pet = pet,
                onClick = { onPetClick(pet.id) },
                onToggleFavorite = { onToggleFavorite(pet) }
            )
        }
    }
}

@Composable
private fun PetCard(
    pet: Pet,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    var scale by remember { mutableStateOf(1f) }
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = 200),
        label = "FavoriteIconScale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            val imageModifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(12.dp))

            when (pet.imageUrl) {
                "local_dog" -> Image(painterResource(R.drawable.icondog), "Cachorro", imageModifier)
                "local_cat" -> Image(painterResource(R.drawable.iconcat), "Gato", imageModifier)
                else -> AsyncImage(
                    model = Uri.parse(pet.imageUrl),
                    contentDescription = pet.name,
                    modifier = imageModifier,
                    contentScale = ContentScale.Crop,
                    error = painterResource(if (pet.specie == "Cachorro") R.drawable.icondog else R.drawable.iconcat)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(pet.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(pet.breed, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(
                onClick = {
                    onToggleFavorite()
                    scale = 1.2f
                },
                modifier = Modifier.scale(animatedScale)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favoritar",
                    tint = if (pet.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            LaunchedEffect(animatedScale) {
                if (animatedScale == 1.2f) {
                    scale = 1f
                }
            }
        }
    }
}

@Composable
fun MoreOptionsMenu(
    onNavigateToSettings: () -> Unit,
    onNavigateToHelp: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.MoreVert, "Mais opções")
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Configurações") },
            onClick = {
                expanded = false
                onNavigateToSettings()
            }
        )
        DropdownMenuItem(
            text = { Text("Ajuda") },
            onClick = {
                expanded = false
                onNavigateToHelp()
            }
        )
    }
}