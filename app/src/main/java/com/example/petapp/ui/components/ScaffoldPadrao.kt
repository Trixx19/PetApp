// SCAFOLD PADRÃO DO APP
package com.example.petapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldPadrao(
    navController: NavController,
    titulo: String,
    mostrarBottomBar: Boolean = true,
    content: @Composable (Modifier) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar( // top bar padrão do aplicativo
                title = { Text(titulo) },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) { // menu 3 pontinhos
                        DropdownMenuItem(
                            text = { Text("Favoritos") },
                            onClick = {
                                navController.navigate("favorites") // função "favoritos" do menu 3 pontinhos
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Configurações") },
                            onClick = {
                                navController.navigate("settings") // função "configurações" do menu 3 pontinhos
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Ajuda") },
                            onClick = {
                                navController.navigate("help") // função "ajuda" do menu 3 pontinhos
                                expanded = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
        bottomBar = {
            if (mostrarBottomBar) { // bottom bar padrão do apl
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = false,
                        onClick = { navController.navigate("home") } // botão home
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
                        label = { Text("Favoritos") },
                        selected = false,
                        onClick = { navController.navigate("favorites") } // botão favoritos
                    )
                }
            }
        }
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}
