package com.example.petapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.petapp.data.model.Pet

@Composable
fun PetItemCard(pet: Pet, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = "Pet Icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = pet.name, style = MaterialTheme.typography.titleLarge)
                Text(text = pet.breed, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }
    }
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = "No pets",
                modifier = Modifier.size(80.dp),
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nenhum pet adicionado ainda.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        }
    }
}