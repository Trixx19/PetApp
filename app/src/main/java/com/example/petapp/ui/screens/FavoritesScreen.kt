// TELA DE PET FAVORITOS
package com.example.petapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.petapp.data.PetRepository
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable // tela que vai exibir os pets favoritos
fun FavoritesScreen(navController: NavController) {
    val favorites by androidx.compose.runtime.remember { // filtra a lista dos pets procurando os que foram marcados
        androidx.compose.runtime.derivedStateOf {
            PetRepository.petList.filter { it.isFavorite }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        if (favorites.isEmpty()) { // verifica se existem favoritos
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                // conteúdo animado para tela vazia
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = androidx.compose.animation.core.tween(durationMillis = 500)) + slideInVertically(
                        initialOffsetY = { fullHeight -> fullHeight / 2 },
                        animationSpec = androidx.compose.animation.core.tween(durationMillis = 500)
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SentimentDissatisfied,
                            contentDescription = "Nenhum favorito",
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Nenhum pet favoritado ainda.",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Adicione seus pets favoritos para vê-los aqui!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else { // se existirem, exibe aqui
            PetList(
                pets = favorites,
                navController = navController,
                modifier = Modifier.padding(padding)
            )
        }
    }
}
