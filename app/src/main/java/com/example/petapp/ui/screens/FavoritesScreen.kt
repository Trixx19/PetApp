package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petapp.ui.PetViewModel
import com.example.petapp.ui.components.PetItemCard

@Composable
fun FavoritesScreen(
    onPetClick: (Int) -> Unit,
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    val favoritePets by viewModel.favoritePets.collectAsStateWithLifecycle()

    if (favoritePets.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Você ainda não favoritou nenhum pet.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = favoritePets, key = { pet -> pet.id }) { pet ->
                PetItemCard(pet = pet, onClick = { onPetClick(pet.id) })
            }
        }
    }
}