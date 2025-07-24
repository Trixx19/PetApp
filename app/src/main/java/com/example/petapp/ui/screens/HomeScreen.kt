package com.example.petapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petapp.ui.PetViewModel
import com.example.petapp.ui.components.EmptyState
import com.example.petapp.ui.components.PetItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPetClick: (Int) -> Unit,
    onAddPetClick: () -> Unit, // <-- O parâmetro que faltava agora está aqui
    viewModel: PetViewModel = viewModel(factory = PetViewModel.Factory)
) {
    val pets by viewModel.allPets.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddPetClick) { // <-- E agora pode ser usado aqui
                Icon(Icons.Default.Add, contentDescription = "Adicionar Pet")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (pets.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = pets, key = { pet -> pet.id }) { pet ->
                        val dismissState = rememberDismissState(
                            confirmValueChange = {
                                if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                                    viewModel.deletePet(pet)
                                    true
                                } else {
                                    false
                                }
                            }
                        )
                        SwipeToDismissBox(
                            state = dismissState,
                            backgroundContent = {
                                val color = when (dismissState.dismissDirection) {
                                    DismissDirection.StartToEnd -> MaterialTheme.colorScheme.errorContainer
                                    DismissDirection.EndToStart -> MaterialTheme.colorScheme.errorContainer
                                    null -> Color.Transparent
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color, shape = RoundedCornerShape(12.dp))
                                        .padding(12.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Excluir",
                                        tint = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                }
                            },
                            content = {
                                PetItemCard(pet = pet, onClick = { onPetClick(pet.id) })
                            }
                        )
                    }
                }
            }
        }
    }
}