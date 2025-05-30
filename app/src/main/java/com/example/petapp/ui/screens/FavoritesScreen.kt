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

@OptIn(ExperimentalMaterial3Api::class)
@Composable // tela que vai exibir os pets marcados como "favorito"
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
                Text("Nenhum pet favoritado ainda.")
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