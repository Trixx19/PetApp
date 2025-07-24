package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petapp.PetApplication
import com.example.petapp.ui.PetViewModel
import com.example.petapp.ui.PetViewModelFactory
// IMPORTAÇÃO EM FALTA QUE CAUSOU O ERRO
import com.example.petapp.ui.components.PetItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    onPetClick: (Int) -> Unit,
    viewModel: PetViewModel = viewModel(
        factory = PetViewModelFactory((LocalContext.current.applicationContext as PetApplication).repository)
    )
) {
    val favoritePets by viewModel.favoritePets.collectAsStateWithLifecycle()

    if (favoritePets.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Sem favoritos",
                    modifier = Modifier.size(80.dp),
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Nenhum pet favorito ainda.", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favoritePets) { pet ->
                PetItemCard(pet = pet, onClick = { onPetClick(pet.id) }) // Agora vai ser encontrado
            }
        }
    }
}